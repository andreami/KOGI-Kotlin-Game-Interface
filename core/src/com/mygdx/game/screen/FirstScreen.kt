package com.mygdx.game.screen

import com.mygdx.game.MyKtxGame
import com.mygdx.game.UNIT_SCALE
import com.mygdx.game.ecs.component.*
import ktx.ashley.entity
import ktx.ashley.with

class FirstScreen(game: MyKtxGame) : GameScreen(game) {

    private val player = game.engine.entity {
        with<TransformComponent> {
            setInitialPosition(300f * UNIT_SCALE, 500f * UNIT_SCALE, 0f)
            size.set(50f, 37f)
        }
        with<GraphicComponent>()
        with<PlayerComponent>()
        with<FacingComponent>()
        with<MoveComponent>()
    }

    override fun render(delta: Float) {
        engine.update(delta)
    }

    override fun dispose() {
    }
}