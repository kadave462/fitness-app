package com.example.myfitnessapp.network

import com.example.myfitnessapp.models.ExerciseResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.File

interface ExerciceService {
    @Headers(
        "Accept: */*",
        "x-rapidapi-key: 0268a875e9msh2c903e8675fa394p14a505jsnac0cc5aa4181",

    )

    @GET("/exercises")
    suspend fun getExercises(
        @Query("limit") limit: Int = 10,
        @Query("equipment") equipment: String = "body weight",
        @Query("sortMethod") sortMethod: String = "target"
    ): List<ExerciseResponse>


    @GET("exercises/name/{name}")
    suspend fun getExercisesByName(
        @Path("name") name: String
    ): List<ExerciseResponse>

    @GET("/exercises/image/{id}")
    suspend fun getGif(
        @Path("id") id: String
    ): File?



}