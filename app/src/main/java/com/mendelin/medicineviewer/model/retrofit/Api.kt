package com.mendelin.medicineviewer.model.retrofit

import com.mendelin.medicineviewer.BuildConfig
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url
import java.util.*

interface Api {
    @GET(BuildConfig.ENPOINT_MEDICINES)
    fun getPageInfo(): Single<String>

    @GET(BuildConfig.ENPOINT_MEDICINES)
    fun getMedicinesByPage(@Query(BuildConfig.QUERY_PAGE) page: Int): Single<String>
}