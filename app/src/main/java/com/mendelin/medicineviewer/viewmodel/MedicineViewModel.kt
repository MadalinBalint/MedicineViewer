package com.mendelin.medicineviewer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mendelin.medicineviewer.model.data.AnmdmInfo
import com.mendelin.medicineviewer.model.data.AnmdmMedicine
import com.mendelin.medicineviewer.model.retrofit.Api
import com.mendelin.medicineviewer.repository.remote.MedicineRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber


class MedicineViewModel(private val service: Api, private val repo: MedicineRepo) : ViewModel() {
    private val isLoading = MutableLiveData(false)
    private val disposables = CompositeDisposable()

    val pageInfo = MutableLiveData<AnmdmInfo>()
    val medicineData = MutableLiveData<List<AnmdmMedicine>>()

    fun getLoadingObservable(): LiveData<Boolean> = isLoading

    fun fetchPageInfo() {
        isLoading.value = true
        disposables.add(
            service.getPageInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { html ->
                        pageInfo.value = repo.parsePageInfo(html)
                        isLoading.value = false
                    },
                    { exception ->
                        Timber.e(exception.stackTraceToString())
                        isLoading.value = false
                    }
                )
        )
    }

    fun fetchMedicinesByPage(page: Int) {
        isLoading.value = true
        disposables.add(
            service.getMedicinesByPage(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { html ->
                        medicineData.value = repo.parseMedicines(html)
                        isLoading.value = false
                    },
                    { exception ->
                        Timber.e(exception.stackTraceToString())
                        isLoading.value = false
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}