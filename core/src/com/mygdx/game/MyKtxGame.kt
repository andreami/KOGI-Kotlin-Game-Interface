package com.mygdx.game

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Application.LOG_DEBUG
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.viewport.FitViewport
import com.mygdx.game.ecs.system.*
import com.mygdx.game.screen.FirstScreen
import com.mygdx.game.screen.GameScreen
import ktx.app.KtxGame
import ktx.log.debug
import ktx.log.logger

private val LOG = logger<MyKtxGame>()
const val V_WIDTH_PIXELS = 135
const val V_HEIGHT_PIXELS = 240
const val UNIT_SCALE = 1 / 8f

class MyKtxGame : KtxGame<GameScreen>() {

    val playerAtlas by lazy { TextureAtlas(Gdx.files.internal("player.atlas")) }

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
            addSystem(RenderSystem(batch, gameViewport))
            addSystem(RemoveSystem())
        }
    }

    override fun create() {
        Gdx.app.logLevel = LOG_DEBUG
        LOG.debug { "Create game instance" }
        addScreen(FirstScreen(this))
        setScreen<FirstScreen>()
    }

    override fun dispose() {
        super.dispose()
        LOG.debug { "Sprites in batch: ${(batch as SpriteBatch).maxSpritesInBatch}" }
        batch.dispose()
        playerAtlas.dispose()
    }

}