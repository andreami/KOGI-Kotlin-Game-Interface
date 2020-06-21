package com.mygdx.game.screen

import com.mygdx.game.MyKtxGame
import com.mygdx.game.UNIT_SCALE
import com.mygdx.game.ecs.component.*
import ktx.ashley.entity
import ktx.ashley.with

class FirstScreen(game: MyKtxGame) : GameScreen(game) {

    private val player = game.engine.entity {
        with<TransformComponent> {
            setInitialPosition(50f, 200f, 0f)
            size.set(50f*2, 37f*2)
        }
        with<MoveComponent>()
        with<GraphicComponent>()
        with<PlayerComponent>()
        with<FacingComponent>()
    }


    private val player2 = game.engine.entity {
        with<TransformComponent> {
            setInitialPosition(150f, 200f, 0f)
            size.set(50f*2, 37f*2)
        }
        with<AnimationComponent> { type = AnimationType.IDLE }
        with<GraphicComponent>()
    }


    private val player3 = game.engine.entity {
        with<TransformComponent> {
            setInitialPosition(300f, 200f, 0f)
            size.set(50f*2, 37f*2)
        }
        with<AnimationComponent> { type = AnimationType.ATTACK1 }
        with<GraphicComponent>()
    }

    override fun render(delta: Float) {
        engine.update(delta)
    }

    override fun dispose() {
    }
}