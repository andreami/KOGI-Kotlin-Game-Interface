package com.mygdx.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Net
import com.badlogic.gdx.net.ServerSocket
import com.badlogic.gdx.net.ServerSocketHints
import com.badlogic.gdx.net.Socket
import com.badlogic.gdx.net.SocketHints
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class NetworkComponent : Component, Pool.Poolable {
    val server: ServerSocket = Gdx.net.newServerSocket(Net.Protocol.TCP, "localhost", 7777, ServerSocketHints())
    val client: Socket = Gdx.net.newClientSocket(Net.Protocol.TCP, "localhost", 7777, SocketHints())

    override fun reset() {
        server.dispose()
        client.dispose()
    }

    companion object {
        val mapper = mapperFor<NetworkComponent>()
    }
}