package com.mendelin.medicineviewer.di

import com.mendelin.medicineviewer.model.retrofit.ServiceProvider
import com.mendelin.medicineviewer.repository.remote.MedicineRepo
import com.mendelin.medicineviewer.viewmodel.MedicineViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val medicineModule = module {
    viewModel { MedicineViewModel(get(), get()) }
    single { MedicineRepo() }
}

val networkModule = module {
    single { ServiceProvider.getService() }
}