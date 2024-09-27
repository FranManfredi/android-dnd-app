package com.example.mobile.apiManager

import com.example.mobile.model.item.Items
import retrofit.Call
import retrofit.http.GET
import retrofit.http.Path

interface ApiService {

    @GET("TgOBxi/items")
    fun getItems(): Call<List<Items>>

}