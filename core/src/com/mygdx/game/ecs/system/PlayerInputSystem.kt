package com.mygdx.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.mygdx.game.ecs.component.Direction
import com.mygdx.game.ecs.component.DirectionComponent
import ktx.ashley.get
import ktx.ashley.oneOf

class PlayerInputSystem : IteratingSystem(
        oneOf(DirectionComponent::class).get()
) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val facing = entity[DirectionComponent.mapper]
        requireNotNull(facing) { "Entity $facing must have FacingComponent." }

        facing.direction = when {
            Gdx.input.isKeyPressed(Input.Keys.A) -> Direction.LEFT
            Gdx.input.isKeyPressed(Input.Keys.D) -> Direction.RIGHT
            else -> Direction.RIGHT
        }
    }
}