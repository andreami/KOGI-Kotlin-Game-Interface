package com.mygdx.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Net
import com.badlogic.gdx.net.ServerSocket
import com.badlogic.gdx.net.ServerSocketHints
import com.badlogic.gdx.net.Socket
import com.badlogic.gdx.net.SocketHints

fun createClient(): Socket {
    return Gdx.net.newClientSocket(Net.Protocol.TCP, "localhost", 7777, SocketHints())
}

fun createServer(): ServerSocket {
    return Gdx.net.newServerSocket(Net.Protocol.TCP, "localhost", 7777, ServerSocketHints())
}