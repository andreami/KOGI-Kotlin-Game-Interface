package com.mygdx.game.ecs.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntityListener
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.mygdx.game.ecs.component.DirectionComponent
import com.mygdx.game.ecs.component.Direction
import com.mygdx.game.ecs.component.GraphicComponent
import com.mygdx.game.ecs.component.PlayerComponent
import ktx.ashley.allOf
import ktx.ashley.get

class PlayerAnimationSystem(
        private val defaultRegion: TextureRegion,
        private val leftRegion: TextureRegion,
        private val rightRegion: TextureRegion
) : IteratingSystem(allOf(PlayerComponent::class, DirectionComponent::class, GraphicComponent::class).get()),
        EntityListener {

    override fun addedToEngine(engine: Engine) {
        super.addedToEngine(engine)
        engine.addEntityListener(family, this)
    }

    override fun removedFromEngine(engine: Engine) {
        super.removedFromEngine(engine)
        engine.removeEntityListener(this)
    }

    override fun entityRemoved(entity: Entity) = Unit

    override fun entityAdded(entity: Entity) {
        entity[GraphicComponent.mapper]?.setSpriteRegion(defaultRegion)
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val facing = entity[DirectionComponent.mapper]
        requireNotNull(facing) { "Entity |entity| must have FacingComponent. entity=$entity" }
        val graphic = entity[GraphicComponent.mapper]
        requireNotNull(graphic) { "Entity |entity| must have GraphicsComponent. entity=$entity" }

        if (facing.direction == facing.lastDirection && graphic.sprite.texture != null) {
            return
        }

        facing.lastDirection = facing.direction
        val region = when (facing.direction) {
            Pair(Direction.LEFT, Direction.DOWN) -> leftRegion
            Pair(Direction.LEFT, Direction.UP) -> leftRegion
            Pair(Direction.RIGHT, Direction.UP) -> rightRegion
            Pair(Direction.RIGHT, Direction.DOWN) -> rightRegion
            else -> defaultRegion
        }

        graphic.setSpriteRegion(region)
    }

}