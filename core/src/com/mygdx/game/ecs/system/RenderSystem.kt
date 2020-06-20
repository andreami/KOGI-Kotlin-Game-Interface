package com.mygdx.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.viewport.Viewport
import com.mygdx.game.ecs.component.GraphicComponent
import com.mygdx.game.ecs.component.TransformComponent
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.graphics.use
import ktx.log.error
import ktx.log.logger

private val LOG = logger<RenderSystem>()

class RenderSystem(
        private val batch: Batch,
        private val gameViewport: Viewport
) : SortedIteratingSystem(
        allOf(GraphicComponent::class, TransformComponent::class).get(),
        compareBy { entity -> entity[TransformComponent.mapper] }
) {

    override fun update(deltaTime: Float) {
        forceSort()
        gameViewport.apply()
        batch.use(gameViewport.camera.combined) {
            super.update(deltaTime)
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity[TransformComponent.mapper]
        requireNotNull(transform) { "Entity |entity| must have TransformerComponent. entity=$entity" }
        val graphic = entity[GraphicComponent.mapper]
        requireNotNull(graphic) { "Entity |entity| must have GraphicsComponent. entity=$entity" }

        if (graphic.sprite.texture == null)
            return LOG.error { "Entity |entity| has no texture for rendering. entity=$entity" }

        graphic.sprite.run {
            draw(batch)
        }
    }
}