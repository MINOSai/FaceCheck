package com.minosai.facecheck.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by minos.ai on 16/03/18.
 */

object WebApi {

    // TODO: Base URL
    const val BASE_URL: String = ""
    var apiService: WebApi? = null

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        apiService = retrofit.create(WebApi::class.java)
    }
}