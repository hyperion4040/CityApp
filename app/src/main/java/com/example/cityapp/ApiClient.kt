package com.example.cityapp

import android.media.Image
import com.example.cityapp.DogImageModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET


interface ApiClient {
//    @GET("/api/breeds/image/random")
    @GET("http://192.168.1.17:8080/getImage?y1=0&x1=0&y2=200&x2=200")
    suspend fun getImage(): Call<ResponseBody>

}