package com.example.cityapp

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



object ApiAdapter {
    // Interface to be used as Retrofit service.
    // We're using Dog.ceo public API.
    val apiClient: ApiClient = Retrofit.Builder()
        .baseUrl("https://dog.ceo")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiClient::class.java)
}