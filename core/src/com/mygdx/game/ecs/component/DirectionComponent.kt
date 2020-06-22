package com.mygdx.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class DirectionComponent : Component, Pool.Poolable {
    var direction = Pair(Direction.RIGHT, Direction.UP)
    var lastDirection = Pair(Direction.RIGHT, Direction.UP)

    override fun reset() {
        direction = Pair(Direction.RIGHT, Direction.UP)
    }

    companion object {
        val mapper = mapperFor<DirectionComponent>()
    }
}

enum class Direction {
    LEFT, RIGHT, UP, DOWN
}