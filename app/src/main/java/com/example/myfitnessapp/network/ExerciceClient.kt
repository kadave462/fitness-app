package com.example.myfitnessapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ExerciceClient {

    private const val BASE_URL = "https://exercisedb.p.rapidapi.com"

    val api: ExerciceService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExerciceService::class.java)
    }
}