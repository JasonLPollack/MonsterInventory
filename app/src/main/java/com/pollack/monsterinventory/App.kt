package com.pollack.monsterinventory

import android.app.Application
import com.pollack.monsterinventory.di.repositoryModule
import com.pollack.monsterinventory.repository.ImageRepository
import com.pollack.monsterinventory.repository.JsonRepository
import org.rewedigital.katana.Component
import org.rewedigital.katana.Katana
import org.rewedigital.katana.Module
import org.rewedigital.katana.android.environment.AndroidEnvironmentContext
import org.rewedigital.katana.dsl.singleton
import java.util.*


class MonsterInventoryApp : Application() {
    companion object {
        private var app: MonsterInventoryApp? = null

        val appComponent get() = requireNotNull(app).appComponent
    }

    //Katana component with app-level modules
    val appComponent = Component(modules = listOf(repositoryModule))

    override fun onCreate() {
        super.onCreate()

        Katana.environmentContext = AndroidEnvironmentContext()
        app = this
    }

}