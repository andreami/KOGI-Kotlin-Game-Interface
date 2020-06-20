package com.mygdx.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.Viewport
import com.mygdx.game.ecs.component.FacingComponent
import com.mygdx.game.ecs.component.FacingDirection
import com.mygdx.game.ecs.component.PlayerComponent
import com.mygdx.game.ecs.component.TransformComponent
import ktx.ashley.allOf
import ktx.ashley.get

private const val TOUCH_TOLERANCE_DISTANCE = 0.2f

class PlayerInputSystem(
        private val gameViewport: Viewport
) : IteratingSystem(
        allOf(PlayerComponent::class, TransformComponent::class, FacingComponent::class).get()
) {
    private val tmpVec = Vector2()
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val facing = entity[FacingComponent.mapper]
        requireNotNull(facing) { "Entity |entity| must have FacingComponent. entity=$entity" }
        val transform = entity[TransformComponent.mapper]
        requireNotNull(transform) { "Entity |entity| must have TransformerComponent. entity=$entity" }

        tmpVec.x = Gdx.input.x.toFloat()
        gameViewport.unproject(tmpVec)
        val distX = tmpVec.x - transform.position.x - transform.size.x * 0.5f

        facing.direction = when {
            distX < -TOUCH_TOLERANCE_DISTANCE -> FacingDirection.LEFT
            distX > TOUCH_TOLERANCE_DISTANCE -> FacingDirection.RIGHT
            else -> FacingDirection.DEFAULT
        }
    }
}