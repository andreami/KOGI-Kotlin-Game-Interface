package com.mygdx.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class PhysicsComponent : Component, Pool.Poolable {
    val bodyDef = BodyDef()
    private val box = PolygonShape()
    val fixtureDef = FixtureDef()


    override fun reset() {
        bodyDef.type = BodyDef.BodyType.DynamicBody
        bodyDef.position.set(10f, 10f)

        box.setAsBox(10f, 10f)

        fixtureDef.shape = box
        fixtureDef.density = 0.5f
        box.dispose()
    }

    companion object {
        val mapper = mapperFor<PhysicsComponent>()
    }
}