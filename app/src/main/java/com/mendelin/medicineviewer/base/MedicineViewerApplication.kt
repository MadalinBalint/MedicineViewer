package com.mendelin.medicineviewer.base

import android.app.Application
import com.mendelin.medicineviewer.di.appComponents
import com.mendelin.medicineviewer.di.networkComponent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MedicineViewerApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MedicineViewerApplication)
            modules(appComponents + networkComponent)
        }
    }
}