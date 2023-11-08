package com.example.agenda_musical_reto1.data.repository.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    const val API_URI = "http://10.5.7.190:8065/api/"

    // creamos el cliente de retrofit con la url de la api
    val retrofitClient: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(API_URI)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiInterface: APIInterface by lazy {
        retrofitClient
            .build()
            .create(APIInterface::class.java)
    }
}