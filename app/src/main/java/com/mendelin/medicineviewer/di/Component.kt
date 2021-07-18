package com.mendelin.medicineviewer.di

import org.koin.core.module.Module

val appComponents = listOf<Module>()

val networkComponent = listOf<Module>(networkModule)