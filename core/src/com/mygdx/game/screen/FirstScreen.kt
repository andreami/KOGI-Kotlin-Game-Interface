package com.mygdx.game.screen

import com.mygdx.game.MyKtxGame
import com.mygdx.game.UNIT_SCALE
import com.mygdx.game.ecs.component.FacingComponent
import com.mygdx.game.ecs.component.GraphicComponent
import com.mygdx.game.ecs.component.PlayerComponent
import com.mygdx.game.ecs.component.TransformComponent
import ktx.ashley.entity
import ktx.ashley.with

class FirstScreen(game: MyKtxGame) : GameScreen(game) {

    private val player = game.engine.entity {
        with<TransformComponent> {
            setInitialPosition(100f * UNIT_SCALE, 100f * UNIT_SCALE, 0f)
        }
        with<GraphicComponent>()
        with<PlayerComponent>()
        with<FacingComponent>()
    }

    override fun render(delta: Float) {
        engine.update(delta)
    }

    override fun dispose() {
    }
}