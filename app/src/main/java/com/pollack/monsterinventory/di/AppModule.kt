package com.pollack.monsterinventory.di

import com.pollack.monsterinventory.repository.ImageRepository
import com.pollack.monsterinventory.repository.JsonRepository
import org.rewedigital.katana.Component
import org.rewedigital.katana.Module
import org.rewedigital.katana.dsl.singleton

val repositoryModule = Module {
    singleton {
        ImageRepository()
    }

    singleton {
        JsonRepository()
    }
}

