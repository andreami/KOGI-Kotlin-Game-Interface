package com.mygdx.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.mygdx.game.MyKtxGame
import com.mygdx.game.ecs.component.*
import ktx.ashley.entity
import ktx.ashley.with

const val PIXELS_TO_METERS = 10f

class FirstScreen(game: MyKtxGame) : GameScreen(game) {

    private val player = game.engine.entity {
        with<TransformComponent> {
            setInitialPosition(50f, 200f, 0f)
            size.set(50f * 2, 37f * 2)
        }
        with<GraphicComponent>()
        with<PlayerComponent>()
        with<DirectionComponent>()
        with<PhysicsComponent>()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        world.step(delta.coerceAtMost(0.25f), 6, 2)
        engine.update(delta)
    }

}

//

//
//
//    private val player2 = game.engine.entity {
//        with<TransformComponent> {
//            setInitialPosition(150f, 200f, 0f)
//            size.set(50f * 2, 37f * 2)
//        }
//        with<AnimationComponent> { type = AnimationType.IDLE }
//        with<GraphicComponent>()
//    }
//
//
//    private val player3 = game.engine.entity {
//        with<TransformComponent> {
//            setInitialPosition(300f, 200f, 0f)
//            size.set(50f * 2, 37f * 2)
//        }
//        with<AnimationComponent> { type = AnimationType.ATTACK1 }
//        with<GraphicComponent>()
//    }
