package com.mendelin.medicineviewer.base

import android.app.Application
import com.mendelin.medicineviewer.di.appComponents
import com.mendelin.medicineviewer.di.networkComponent
import com.mendelin.medicineviewer.logging.TimberPlant
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MedicineViewerApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        TimberPlant.plantTimberDebugLogger()

        startKoin {
            androidLogger()
            androidContext(this@MedicineViewerApplication)
            modules(appComponents + networkComponent)
        }
    }
}