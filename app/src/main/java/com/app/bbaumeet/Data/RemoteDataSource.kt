package com.app.befit.data.network

import androidx.viewbinding.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RemoteDataSource {
    companion object {
        private const val BASE_URL = "https://befit.digitaldarwin.in/myphio-user-management/"
    }

    fun <Api> buildApi(api: Class<Api>, authToken: String? = null, req: Int? = null): Api {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        chain.proceed(chain.request().newBuilder().also {
                            if (req == null){
                                it.addHeader("Authorization", "$authToken")
                            }
                            if (authToken == null){
                                it.addHeader("req", "$req")
                                it.addHeader("accept", "*/*")
                            }
                        }.build())
                    }.also { client ->
                        if (BuildConfig.DEBUG) {
                            val logging = HttpLoggingInterceptor()
                            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                            client.addInterceptor(logging)
                        }
                    }.build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }
}