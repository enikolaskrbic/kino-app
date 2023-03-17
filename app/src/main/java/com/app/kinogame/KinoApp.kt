package com.app.kinogame

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KinoApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}
