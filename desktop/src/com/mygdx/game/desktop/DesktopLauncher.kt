package com.mygdx.game.desktop

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.mygdx.game.MyKtxGame

fun main() {
    Lwjgl3Application(
            MyKtxGame(),
            Lwjgl3ApplicationConfiguration().apply {
                setTitle("MyKtxGame")
                setWindowSizeLimits(640, 480, -1, -1)
                setWindowedMode(640, 480)
            })
}