package com.example.mvi_android.network

class AnimalRepo(private val api : ApiInterface) {
    suspend fun getAnimals() = api.getAnimals()
}