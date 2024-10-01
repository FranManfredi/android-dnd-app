package com.example.mobile.apiManager

import com.example.mobile.data.CharClass
import com.example.mobile.data.Item
import com.example.mobile.data.Race
import com.example.mobile.data.Spell
import com.example.mobile.data.Weapon
import retrofit.Call
import retrofit.http.GET

interface ApiService {

    @GET("TgOBxi/items")
    fun getItems(): Call<List<Item>>

    @GET("83gZrQ/weapons")
    fun getWeapons(): Call<List<Weapon>>

    @GET("sRklr1/spells")
    fun getSpells(): Call<List<Spell>>

    @GET("/nHJ2Vq/races")
    fun getRaces(): Call<List<Race>>

    @GET("/Erl1EB/classes")
    fun getClasses(): Call<List<CharClass>>
}