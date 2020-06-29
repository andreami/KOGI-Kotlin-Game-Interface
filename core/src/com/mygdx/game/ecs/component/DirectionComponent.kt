package com.mygdx.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class DirectionComponent : Component, Pool.Poolable {
    var direction = Direction.RIGHT
    var lastDirection = Direction.RIGHT

    override fun reset() {
        direction = Direction.RIGHT
    }

    companion object {
        val mapper = mapperFor<DirectionComponent>()
    }
}

enum class Direction {
    LEFT, RIGHT
}