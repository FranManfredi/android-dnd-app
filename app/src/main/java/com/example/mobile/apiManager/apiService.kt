package com.example.mobile.apiManager

import com.example.mobile.model.item.Items
import com.example.mobile.model.weapon.Weapons
import retrofit.Call
import retrofit.http.GET
import retrofit.http.Path

interface ApiService {

    @GET("TgOBxi/items")
    fun getItems(): Call<List<Items>>

    @GET("83gZrQ/weapons")
    fun getWeapons(): Call<List<Weapons>>

//    @GET("sRkIr1/spells")
//    fun getSpells(): Call<List<Spells>>
//
//    @GET("/nHJ2Vq/races")
//    fun getRaces(): Call<List<Races>>
//
//    @GET("/ErlEB/classes")
//    fun getClasses(): Call<List<Classes>>
}