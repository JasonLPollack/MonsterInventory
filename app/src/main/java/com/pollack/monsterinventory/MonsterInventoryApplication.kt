package com.pollack.monsterinventory

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

class MonsterInventoryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }

}