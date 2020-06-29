package com.mygdx.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.physics.box2d.World
import com.mygdx.game.ecs.component.GraphicComponent
import com.mygdx.game.ecs.component.MoveComponent
import com.mygdx.game.ecs.component.PhysicsComponent
import com.mygdx.game.screen.PIXELS_TO_METERS
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.box2d.body
import ktx.graphics.use

class PhysicsSystem(private val world: World, private val batch: Batch) : IteratingSystem(
        allOf(PhysicsComponent::class, MoveComponent::class, GraphicComponent::class).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val physics = entity[PhysicsComponent.mapper]
        requireNotNull(physics)
        val graphics = entity[GraphicComponent.mapper]
        requireNotNull(graphics)

        val body = world.body {
            physics.bodyDef
            physics.fixtureDef
        }

        val sprite = Sprite()

        val bodyXPosition = body.position.x * PIXELS_TO_METERS

        batch.use {
            batch.draw(sprite, sprite.x, sprite.y, sprite.originX,
                    sprite.originY,
                    sprite.width, sprite.height, sprite.scaleX, sprite.scaleY, sprite.rotation)
        }

    }

//    private fun movePlayer(
//            transform: TransformComponent,
//            move: MoveComponent,
//            direction: DirectionComponent,
//            physics: PhysicsComponent,
//            deltaTime: Float
//    ) {
//
//        move.speed.set(when (direction.direction) {
//            Direction.LEFT -> Vector2(min(0f, move.speed.x - HOR_ACCELERATION * deltaTime), 10f)
//            Direction.RIGHT -> Vector2(max(0f, move.speed.x + HOR_ACCELERATION * deltaTime), 10f)
//            else -> Vector2(0f, 0f)
//        })
//
//
//
//        move.speed.set(MathUtils.clamp(move.speed.x, -MAX_HOR_SPEED, MAX_HOR_SPEED), 0f)
//
//        transform.position.x = MathUtils.clamp(
//                transform.position.x + move.speed.x * deltaTime,
//                0f,
//                V_WIDTH_PIXELS - transform.size.x
//        )
//    }
}