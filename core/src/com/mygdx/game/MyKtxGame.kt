package com.mygdx.game

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FitViewport
import com.mygdx.game.ecs.system.RenderSystem
import com.mygdx.game.screen.FirstScreen
import com.mygdx.game.screen.GameScreen
import ktx.app.KtxGame
import ktx.log.debug
import ktx.log.logger

private val LOG = logger<MyKtxGame>()

class MyKtxGame : KtxGame<GameScreen>() {

    val gameViewport = FitViewport(640f, 480f)
    val batch: Batch by lazy { SpriteBatch() }
    val engine: Engine by lazy {
        PooledEngine().apply {
            addSystem(RenderSystem(batch, gameViewport))
        }
    }

    override fun create() {
        addScreen(FirstScreen(this))
        setScreen<FirstScreen>()
    }

    override fun dispose() {
        super.dispose()
        LOG.debug { "Sprites in batch: ${(batch as SpriteBatch).maxSpritesInBatch}" }
        batch.dispose()
    }

}