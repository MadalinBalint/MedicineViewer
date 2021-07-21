package com.mendelin.medicineviewer.di

import org.koin.core.module.Module

val appComponents = listOf<Module>(
    medicineModule
)

val networkComponent = listOf<Module>(networkModule)