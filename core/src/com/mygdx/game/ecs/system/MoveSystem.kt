package com.mygdx.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.V_WIDTH_PIXELS
import com.mygdx.game.ecs.component.*
import ktx.ashley.allOf
import ktx.ashley.get
import kotlin.math.max
import kotlin.math.min

private const val HOR_ACCELERATION = 50.5f
private const val MAX_HOR_SPEED = 50.5f

class MoveSystem(
) : IteratingSystem(allOf(TransformComponent::class, MoveComponent::class, PhysicsComponent::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity[TransformComponent.mapper]
        requireNotNull(transform) { "Entity $entity must have a TransformComponent." }
        val move = entity[MoveComponent.mapper]
        requireNotNull(move) { "Entity $entity must have a MoveComponent." }
        val physics = entity[PhysicsComponent.mapper]
        requireNotNull(physics) { "Entity $entity must have a PhysicsComponent." }

        entity[DirectionComponent.mapper]?.let { facing ->
            movePlayer(transform, move, facing, physics, deltaTime)
        }
    }

    private fun movePlayer(
            transform: TransformComponent,
            move: MoveComponent,
            direction: DirectionComponent,
            physics: PhysicsComponent,
            deltaTime: Float
    ) {

        move.speed.set(when (direction.direction) {
            Direction.LEFT -> Vector2(min(0f, move.speed.x - HOR_ACCELERATION * deltaTime), 10f)
            Direction.RIGHT -> Vector2(max(0f, move.speed.x + HOR_ACCELERATION * deltaTime), 10f)
        })



        move.speed.set(MathUtils.clamp(move.speed.x, -MAX_HOR_SPEED, MAX_HOR_SPEED), 0f)

        transform.position.x = MathUtils.clamp(
                transform.position.x + move.speed.x * deltaTime,
                0f,
                V_WIDTH_PIXELS - transform.size.x
        )
    }

}
