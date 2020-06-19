package com.mygdx.game

import com.badlogic.gdx.Screen
import com.mygdx.game.screen.FirstScreen
import ktx.app.KtxGame

class MyGdxGame : KtxGame<Screen>() {

    override fun create() {
        addScreen(FirstScreen())
        setScreen<FirstScreen>()
    }

}