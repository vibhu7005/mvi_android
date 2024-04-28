package com.example.mvi_android.network

import com.example.mvi_android.model.Animal
import retrofit2.http.GET

interface ApiInterface {
    @GET("animals.json")
    suspend fun getAnimals() : List<Animal>
}