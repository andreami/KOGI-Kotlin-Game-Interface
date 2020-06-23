package com.mygdx.game

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Application.LOG_DEBUG
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Net
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.net.ServerSocketHints
import com.badlogic.gdx.net.SocketHints
import com.badlogic.gdx.utils.viewport.FitViewport
import com.mygdx.game.ecs.system.*
import com.mygdx.game.screen.FirstScreen
import com.mygdx.game.screen.GameScreen
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ktx.app.KtxGame
import ktx.async.KtxAsync
import ktx.async.newAsyncContext
import ktx.log.debug
import ktx.log.logger
import java.io.BufferedReader
import java.io.InputStreamReader

private val log = logger<MyKtxGame>()
const val V_WIDTH_PIXELS = 640
const val V_HEIGHT_PIXELS = 480

class MyKtxGame : KtxGame<GameScreen>() {

    private val playerAtlas by lazy { TextureAtlas(Gdx.files.internal("player.atlas")) }
    private val graphicAtlas by lazy { TextureAtlas(Gdx.files.internal("graphic.atlas")) }

    val gameViewport = FitViewport(V_WIDTH_PIXELS.toFloat(), V_HEIGHT_PIXELS.toFloat())
    val batch: Batch by lazy { SpriteBatch() }
    val engine: Engine by lazy {
        PooledEngine().apply {
            addSystem(PlayerInputSystem(gameViewport))
            addSystem(MoveSystem())
            addSystem(PlayerAnimationSystem(
                    playerAtlas.findRegion("adventurer-idle-00"),
                    playerAtlas.findRegion("adventurer-crouch-00"),
                    playerAtlas.findRegion("adventurer-idle-00")
            ))
            addSystem(AnimationSystem(graphicAtlas))
            addSystem(RenderSystem(batch, gameViewport))
            addSystem(RemoveSystem())
        }
    }

    override fun create() {
        Gdx.app.logLevel = LOG_DEBUG
        log.debug { "Create game instance" }
        addScreen(FirstScreen(this))
        setScreen<FirstScreen>()
    }

    override fun dispose() {
        super.dispose()
        log.debug { "Sprites in batch: ${(batch as SpriteBatch).maxSpritesInBatch}" }
        batch.dispose()
        graphicAtlas.dispose()
        playerAtlas.dispose()
    }

}