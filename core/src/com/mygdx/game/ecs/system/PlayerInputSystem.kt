package com.mygdx.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.Viewport
import com.mygdx.game.ecs.component.Direction
import com.mygdx.game.ecs.component.DirectionComponent
import com.mygdx.game.ecs.component.PlayerComponent
import com.mygdx.game.ecs.component.TransformComponent
import ktx.ashley.allOf
import ktx.ashley.get

class PlayerInputSystem(
        private val gameViewport: Viewport
) : IteratingSystem(
        allOf(PlayerComponent::class, TransformComponent::class, DirectionComponent::class).get()
) {
    private val tmpVec = Vector2()
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val facing = entity[DirectionComponent.mapper]
        requireNotNull(facing) { "Entity |entity| must have FacingComponent. entity=$entity" }
        val transform = entity[TransformComponent.mapper]
        requireNotNull(transform) { "Entity |entity| must have TransformerComponent. entity=$entity" }

        tmpVec.x = Gdx.input.getX().toFloat()
        tmpVec.y = Gdx.input.y.toFloat()
        gameViewport.unproject(tmpVec)
        val distX = tmpVec.x - transform.position.x - transform.size.x * 0.5f
        val distY = tmpVec.y - transform.position.y - transform.size.y * 0.5f


        facing.direction = when {
            distX < 0 && distY < 0 -> Pair(Direction.LEFT, Direction.DOWN)
            distX > 0 && distY > 0 -> Pair(Direction.RIGHT, Direction.UP)
            distX < 0 && distY > 0 -> Pair(Direction.LEFT, Direction.UP)
            distX > 0 && distY < 0 -> Pair(Direction.RIGHT, Direction.DOWN)
            else -> Pair(Direction.RIGHT, Direction.UP)
        }
    }
}