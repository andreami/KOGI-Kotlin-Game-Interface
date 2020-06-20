package com.mygdx.game

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Application.LOG_DEBUG
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.viewport.FitViewport
import com.mygdx.game.ecs.system.PlayerAnimationSystem
import com.mygdx.game.ecs.system.PlayerInputSystem
import com.mygdx.game.ecs.system.RenderSystem
import com.mygdx.game.screen.FirstScreen
import com.mygdx.game.screen.GameScreen
import ktx.app.KtxGame
import ktx.log.debug
import ktx.log.logger

private val LOG = logger<MyKtxGame>()
const val UNIT_SCALE = 1 / 8f

class MyKtxGame : KtxGame<GameScreen>() {

    val playerAtlas by lazy { TextureAtlas(Gdx.files.internal("player.atlas")) }

    val gameViewport = FitViewport(640f, 480f)
    val batch: Batch by lazy { SpriteBatch() }
    val engine: Engine by lazy {
        PooledEngine().apply {
            addSystem(PlayerInputSystem(gameViewport))
            addSystem(PlayerAnimationSystem(
                    playerAtlas.findRegion("adventurer-idle-00"),
                    playerAtlas.findRegion("adventurer-crouch-00"),
                    playerAtlas.findRegion("adventurer-idle-00")
            ))
            addSystem(RenderSystem(batch, gameViewport))
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