package com.example.mobile.apiManager
import com.example.mobile.model.character.Character
import retrofit.Call
import retrofit.http.GET
import retrofit.http.Path

interface ApiService {

    @GET("api/classes/{class_name}")
    fun getHitDice(@Path("class_name") className: String): Call<Int>
}