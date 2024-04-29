package com.example.mvi_android.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    const val BASE_URl = "https://raw.githubusercontent.com/CatalinStefan/animalApi/master/"
    private fun getRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api : ApiInterface = getRetrofit().create(ApiInterface::class.java)
}