package com.mendelin.medicineviewer.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mendelin.medicineviewer.databinding.FragmentMedicineViewerBinding
import com.mendelin.medicineviewer.viewmodel.MedicineViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class MedicineViewerFragment : Fragment() {

    private val viewModel by sharedViewModel<MedicineViewModel>()
    private var binding: FragmentMedicineViewerBinding? = null
    private lateinit var drugAdapter: MedicineAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMedicineViewerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        populateUI()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setupUI() {
        drugAdapter = MedicineAdapter()
        binding?.recyclerResults?.apply {
            adapter = drugAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            isNestedScrollingEnabled = true
        }
    }

    private fun populateUI() {
        viewModel.fetchPageInfo()
        viewModel.fetchMedicinesByPage(1)
    }

    private fun observeViewModel() {
        viewModel.pageInfo.observe(viewLifecycleOwner, { pages ->
            Timber.e("Pages = $pages")
        })

        viewModel.medicineData.observe(viewLifecycleOwner, { list ->
            if (list.isNotEmpty()) {
                Timber.e("Medicine # = ${list.size}")

                list.forEach(::println)
                drugAdapter.add(list)
            }
        })
    }
}