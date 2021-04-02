package com.example.locationfinder.api

import com.example.locationfinder.constant.McConstants.BASE_URL
import com.example.locationfinder.constant.McConstants.KAKAO_KEY
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * McLocationService
 */
abstract class McLocationService {
    companion object {
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val interceptor = Interceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "KakaoAK $KAKAO_KEY").build()
                return@Interceptor chain.proceed(request)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(interceptor)
                .build()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val mcApi: McApi by lazy { retrofit.create(McApi::class.java) }
    }
}