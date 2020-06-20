package com.mygdx.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.mygdx.game.MyKtxGame
import com.mygdx.game.ecs.component.GraphicsComponent
import com.mygdx.game.ecs.component.TransformComponent
import ktx.ashley.entity
import ktx.ashley.with

class FirstScreen(game: MyKtxGame) : GameScreen(game) {

    private val playerTexture = Texture(Gdx.files.internal("adventurer-idle-00.png"))

    private val player = game.engine.entity {
        with<TransformComponent> {
            position.set(10f, 10f, 0f)
        }
        with<GraphicsComponent> {
            sprite.run {
                setRegion(playerTexture)
                setSize(texture.width.toFloat(), texture.height.toFloat())
            }
        }
    }

    override fun render(delta: Float) {
        engine.update(delta)
    }

    override fun dispose() {
        playerTexture.dispose()
    }
}