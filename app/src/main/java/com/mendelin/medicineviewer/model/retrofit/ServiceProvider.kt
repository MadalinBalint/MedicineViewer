package com.mendelin.medicineviewer.model.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mendelin.medicineviewer.BuildConfig
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

object ServiceProvider {
    private const val TRY_COUNT = 3
    private const val TRY_PAUSE_BETWEEN = 1000L

    private var api: Api? = null

    private fun okHttpClient(): OkHttpClient =
        OkHttpClient.Builder().apply {
            readTimeout(60, TimeUnit.SECONDS)
            connectTimeout(60, TimeUnit.SECONDS)
            addInterceptor(
                Interceptor { chain ->
                    val builder = chain.request().newBuilder()
                    builder.header("accept", "*/*")
                    builder.header("Content-Type", "application/json")

                    var response = chain.proceed(builder.build())

                    /* Automatically retry the call for N times if you receive a server error (5xx) */
                    var tryCount = 0
                    while (response.code() in 500..599 && tryCount < TRY_COUNT) {
                        try {
                            response.close()
                            Thread.sleep(TRY_PAUSE_BETWEEN)
                            response = chain.proceed(builder.build())
                            Timber.e("Request is not successful - $tryCount")
                        } catch (e: Exception) {
                            Timber.e(e.localizedMessage)
                        } finally {
                            tryCount++
                        }
                    }

                    /* Intercept empty body response */
                    if (response.body()?.contentLength() == 0L) {
                        val contentType: MediaType? = response.body()!!.contentType()
                        val body: ResponseBody = ResponseBody.create(contentType, "{}")
                        return@Interceptor response.newBuilder().body(body).build()
                    }

                    return@Interceptor response
                }
            )
        }.build()

    private fun getGsonClient(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    fun getService(): Api {
        if (api == null) {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(Api::class.java)
        }

        return api!!
    }
}