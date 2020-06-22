package com.mygdx.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.V_HEIGHT_PIXELS
import com.mygdx.game.V_WIDTH_PIXELS
import com.mygdx.game.ecs.component.*
import ktx.ashley.allOf
import ktx.ashley.exclude
import ktx.ashley.get
import kotlin.math.max
import kotlin.math.min

private const val VER_ACCELERATION = 50.5f
private const val HOR_ACCELERATION = 50.5f
private const val MAX_HOR_SPEED = 50.5f
private const val MAX_VER_SPEED = 50.5f
private const val UPDATE_RATE = 1 / 25f

class MoveSystem(
) : IteratingSystem(allOf(TransformComponent::class, MoveComponent::class).exclude(RemoveComponent::class).get()) {
    private var accumulator = 0f

    override fun update(deltaTime: Float) {
        accumulator += deltaTime
        while (accumulator >= UPDATE_RATE) {
            accumulator -= UPDATE_RATE
            super.update(UPDATE_RATE)
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity[TransformComponent.mapper]
        require(transform != null) { "Entity |entity| must have a TransformComponent. entity=$entity" }
        val move = entity[MoveComponent.mapper]
        require(move != null) { "Entity |entity| must have a MoveComponent. entity=$entity" }

        val player = entity[PlayerComponent.mapper]
        if (player != null) {
            entity[DirectionComponent.mapper]?.let { facing ->
                movePlayer(transform, move, player, facing, deltaTime)
            }
        } else {
            moveEntity(transform, move, deltaTime)
        }
    }

    private fun movePlayer(
            transform: TransformComponent,
            move: MoveComponent,
            player: PlayerComponent,
            direction: DirectionComponent,
            deltaTime: Float
    ) {

        move.speed.set(when (direction.direction) {
            Pair(Direction.LEFT, Direction.DOWN) -> Vector2(min(0f, move.speed.x - HOR_ACCELERATION * deltaTime), min(0f, move.speed.y - VER_ACCELERATION * deltaTime))
            Pair(Direction.LEFT, Direction.UP) -> Vector2(min(0f, move.speed.x - HOR_ACCELERATION * deltaTime), max(0f, move.speed.y + VER_ACCELERATION * deltaTime))
            Pair(Direction.RIGHT, Direction.UP) -> Vector2(max(0f, move.speed.x + HOR_ACCELERATION * deltaTime), max(0f, move.speed.y + VER_ACCELERATION * deltaTime))
            Pair(Direction.RIGHT, Direction.DOWN) -> Vector2(max(0f, move.speed.x + HOR_ACCELERATION * deltaTime), min(0f, move.speed.y - VER_ACCELERATION * deltaTime))
            else -> Vector2(0f, 0f)
        })

        move.speed.set(
                MathUtils.clamp(move.speed.x, -MAX_HOR_SPEED, MAX_HOR_SPEED),
                MathUtils.clamp(move.speed.y, -MAX_VER_SPEED, MAX_VER_SPEED))

        moveEntity(transform, move, deltaTime)
    }

    private fun moveEntity(
            transform: TransformComponent,
            move: MoveComponent,
            deltaTime: Float
    ) {
        transform.position.x = MathUtils.clamp(
                transform.position.x + move.speed.x * deltaTime,
                0f,
                V_WIDTH_PIXELS - transform.size.x
        )
        transform.position.y = MathUtils.clamp(
                transform.position.y + move.speed.y * deltaTime,
                1f,
                V_HEIGHT_PIXELS + 1f - transform.size.y
        )

    }
}
