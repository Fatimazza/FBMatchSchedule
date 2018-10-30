package io.github.fatimazza.fbmatchschedule.network

import io.github.fatimazza.fbmatchschedule.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper private constructor() {

    val theSportDBApi: TheSportDBApi

    init {
        val mRetrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(setupLoggingInterceptor().build())
                .build()
        theSportDBApi = mRetrofit.create(TheSportDBApi::class.java)
    }

    private fun setupLoggingInterceptor(): OkHttpClient.Builder {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        return httpClient
    }

    companion object {

        private var retrofitHelper: RetrofitHelper? = null

        val instance: RetrofitHelper
            get() {
                if (retrofitHelper == null) {
                    retrofitHelper = RetrofitHelper()
                }
                return retrofitHelper as RetrofitHelper
            }
    }

}