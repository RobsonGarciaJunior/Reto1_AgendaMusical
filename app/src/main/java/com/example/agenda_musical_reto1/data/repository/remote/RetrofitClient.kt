package com.example.agenda_musical_reto1.data.repository.remote

import com.example.agenda_musical_reto1.utils.MyApp
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val API_URI = "http://10.0.2.2:8065/api/"

    private var client = OkHttpClient.Builder().addInterceptor { chain ->
        val authToken = MyApp.userPreferences.fetchAuthToken()
        val newRequest: Request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $authToken")
            .build()
        chain.proceed(newRequest)
    }.build()

    // creamos el cliente de retrofit con la url de la api
    private val retrofitClient: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .client(client)
            .baseUrl(API_URI)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiInterface: APIInterface by lazy {
        retrofitClient
            .build()
            .create(APIInterface::class.java)
    }
}