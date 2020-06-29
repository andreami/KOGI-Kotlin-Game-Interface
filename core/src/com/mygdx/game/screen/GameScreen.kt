package com.mygdx.game.screen

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.viewport.Viewport
import com.mygdx.game.MyKtxGame
import ktx.app.KtxScreen

abstract class GameScreen(
        val game: MyKtxGame,
        val batch: Batch = game.batch,
        val gameViewport: Viewport = game.gameViewport,
        val engine: Engine = game.engine,
        val world: World = game.world
//        val sprite: Sprite = game.sprite,
//        val body: Body = game.body
) : KtxScreen {
    override fun resize(width: Int, height: Int) {
        gameViewport.update(width, height, true)
    }
}