package com.minosai.facecheck.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by minos.ai on 16/03/18.
 */

object ApiClient {

    // TODO: Base URL
    const val BASE_URL: String = ""

    // TODO: Initialize OkHTTP client
//    val client = OkHttpClient().newBuilder()
//            .cache(cache)
//            .addInterceptor(LastFmRequestInterceptor(apiKey, cacheDuration))
//            .addInterceptor(HttpLoggingInterceptor().apply {
//                level = if (BuildConfig.DEBUG) Level.BODY else Level.NONE
//            })
//            .build()
//    }

    val retrofit = Retrofit.Builder()
            .baseUrl("http://ws.audioscrobbler.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val apiService = retrofit.create(ApiClient::class.java)
}