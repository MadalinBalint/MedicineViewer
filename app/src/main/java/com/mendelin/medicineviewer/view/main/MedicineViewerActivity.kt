package com.mendelin.medicineviewer.view.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mendelin.medicineviewer.databinding.ActivityMedicineviewerBinding
import com.mendelin.medicineviewer.viewmodel.MedicineViewModel
import org.koin.android.compat.ScopeCompat.viewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class MedicineViewerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMedicineviewerBinding
    private val viewModel by viewModel<MedicineViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMedicineviewerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getLoadingObservable().observe(this, { status ->
            Timber.e("Status = $status")
            binding.progressMain.visibility = if (status) View.VISIBLE else View.INVISIBLE
        })
    }
}