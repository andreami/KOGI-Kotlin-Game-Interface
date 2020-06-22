package com.mygdx.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Net
import com.badlogic.gdx.net.ServerSocketHints
import com.mygdx.game.ecs.component.NetworkComponent
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.async.KtxAsync
import ktx.async.newSingleThreadAsyncContext
import ktx.log.debug
import ktx.log.logger
import java.io.BufferedReader
import java.io.InputStreamReader

private val log = logger<CommunicaitonSystem>()

class CommunicaitonSystem : IteratingSystem(
        allOf(NetworkComponent::class).get()
) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val network = entity[NetworkComponent.mapper]
        requireNotNull(network) { "The NetworkComponent cannot be null." }
        val buff = ByteArray(10)

        val executor = newSingleThreadAsyncContext()
        KtxAsync.launch {
            network.client.outputStream.use { it.write("PING".toByteArray()) }
            log.debug { "Message sent" }
            withContext(executor) {
                val client = network.server.accept(null)
                val message = client.inputStream.use { it.read(buff) }
                log.debug { "Searching client...." }
                log.debug { "got client message: $message" }
                client.outputStream.use { it.write("PONG".toByteArray())}
            }
        }


    }
}