package com.mygdx.game.screen

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.ecs.component.GraphicsComponent
import com.mygdx.game.ecs.component.TransformComponent
import ktx.app.KtxScreen
import ktx.ashley.entity
import ktx.ashley.get
import ktx.ashley.with
import ktx.graphics.use

class FirstScreen : KtxScreen {

    private val font = BitmapFont()
    private val playerTexture = Texture(Gdx.files.internal("badlogic.jpg"))
    private val engine = PooledEngine()
    private val player = engine.entity {
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

    private val batch = SpriteBatch().apply {
        color = Color.WHITE
    }

    override fun render(delta: Float) {
        engine.update(delta)
        batch.use {
            font.draw(it, "Hello Kotlin!", 100f, 100f)
            player[GraphicsComponent.mapper]?.sprite?.draw(it)
        }
    }

    override fun dispose() {
        font.dispose()
        playerTexture.dispose()
        batch.dispose()
    }
}