package com.example.mobile.apiManager

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.mobile.R
import com.example.mobile.data.Item
import com.example.mobile.model.item.Items
import com.example.mobile.model.weapon.Weapons
import retrofit.Call
import retrofit.Callback
import retrofit.GsonConverterFactory
import retrofit.Response
import retrofit.Retrofit
import javax.inject.Inject

class ApiServiceImpl @Inject constructor() {

    fun getItems(context: Context, onSuccess: (List<Item>) -> Unit, onFail: () -> Unit, loadingFinished: () -> Unit) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(context.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: ApiService = retrofit.create(ApiService::class.java)

        val call: Call<List<Item>> = service.getItems()

        call.enqueue(object : Callback<List<Item>> {

            override fun onResponse(response: Response<List<Item>>?, retrofit: Retrofit?) {
                loadingFinished()
                if (response?.isSuccess == true) {
                    val itemsList = response.body()
                    onSuccess(itemsList)
                } else {
                    onFail()
                }
            }

            override fun onFailure(t: Throwable?) {
                Log.e("API", t?.message!!)
                loadingFinished()
                onFail()
            }
        })
    }

    fun getWeapons(context: Context, onSuccess: (List<Weapons>) -> Unit, onFail: () -> Unit, loadingFinished: () -> Unit) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(context.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: ApiService = retrofit.create(ApiService::class.java)

        val call: Call<List<Weapons>> = service.getWeapons()

        call.enqueue(object : Callback<List<Weapons>> {
            override fun onResponse(response: Response<List<Weapons>>?, retrofit: Retrofit?) {
                loadingFinished()
                if (response?.isSuccess == true) {
                    val weaponsList = response.body()
                    onSuccess(weaponsList)
                } else {
                    onFail()
                }            }

            override fun onFailure(t: Throwable?) {
                Log.e("API", t?.message!!)
                loadingFinished()
                onFail()            }
        })
    }


}