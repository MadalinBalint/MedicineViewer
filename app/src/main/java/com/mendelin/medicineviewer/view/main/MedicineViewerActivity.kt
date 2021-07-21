package com.mendelin.medicineviewer.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mendelin.medicineviewer.R
import com.mendelin.medicineviewer.viewmodel.MedicineViewModel
import org.koin.android.ext.android.inject
import timber.log.Timber


class MedicineViewerActivity : AppCompatActivity() {
    private val viewModel: MedicineViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.fetchPageInfo()
        viewModel.fetchMedicinesByPage(1)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.pageInfo.observe(this, { pages ->
            Timber.e("Pages = $pages")
        })

        viewModel.medicineData.observe(this, { list ->
            if (list.isNotEmpty()) {
                Timber.e("Medicine # = ${list.size}")

                list.forEach(::println)
            }
        })
    }
}