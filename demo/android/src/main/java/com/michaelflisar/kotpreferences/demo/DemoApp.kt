package com.michaelflisar.kotpreferences.demo

import android.app.Application
import com.michaelflisar.composethemer.ComposeTheme
import com.michaelflisar.composethemer.themes.ComposeThemes

class DemoApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ComposeTheme.register(*ComposeThemes.ALL.toTypedArray())
    }

}