package com.mygdx.game.desktop

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.mygdx.game.MyGdxGame
import com.mygdx.game.MyKtxGame

fun main() {
    Lwjgl3Application(
            MyGdxGame(),
            Lwjgl3ApplicationConfiguration().apply {
                setTitle("MyKtxGame")
                setWindowSizeLimits(800, 600, -1, -1)
                setWindowedMode(800, 600)
            })
}