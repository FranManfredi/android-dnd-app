package com.example.mobile.apiManager

import android.content.Context
import android.util.Log
import com.example.mobile.R
import com.example.mobile.data.CharClass
import com.example.mobile.data.Item
import com.example.mobile.data.Race
import com.example.mobile.data.Spell
import com.example.mobile.data.Weapon
import retrofit.Call
import retrofit.Callback
import retrofit.GsonConverterFactory
import retrofit.Response
import retrofit.Retrofit
import javax.inject.Inject

class ApiServiceImpl @Inject constructor() {

    inline fun <reified T> fetchFromApi(
        context: Context,
        apiCall: Call<List<T>>,
        crossinline onSuccess: (List<T>) -> Unit,
        crossinline onFail: () -> Unit,
        crossinline loadingFinished: () -> Unit
    ) {
        apiCall.enqueue(object : Callback<List<T>> {
            override fun onResponse(response: Response<List<T>>?, retrofit: Retrofit?) {
                loadingFinished()
                if (response != null) {
                    if (response.isSuccess && response.body() != null) {
                        if (response != null) {
                            onSuccess(response.body()!!)
                        }
                    } else {
                        onFail()
                    }
                }
            }

            override fun onFailure(t: Throwable?) {
                if (t != null) {
                    Log.e("API", t.message ?: "Unknown error")
                }
                loadingFinished()
                onFail()
            }
        })
    }

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

    fun getWeapons(context: Context, onSuccess: (List<Weapon>) -> Unit, onFail: () -> Unit, loadingFinished: () -> Unit) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(context.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: ApiService = retrofit.create(ApiService::class.java)

        val call: Call<List<Weapon>> = service.getWeapons()

        call.enqueue(object : Callback<List<Weapon>> {
            override fun onResponse(response: Response<List<Weapon>>?, retrofit: Retrofit?) {
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

    fun getClasses(context: Context, onSuccess: (List<CharClass>) -> Unit, onFail: () -> Unit, loadingFinished: () -> Unit) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(context.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: ApiService = retrofit.create(ApiService::class.java)

        val call: Call<List<CharClass>> = service.getClasses()

        fetchFromApi(context, call, onSuccess, onFail, loadingFinished)
    }

    fun getRaces(context: Context, onSuccess: (List<Race>) -> Unit, onFail: () -> Unit, loadingFinished: () -> Unit) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(context.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: ApiService = retrofit.create(ApiService::class.java)

        val call: Call<List<Race>> = service.getRaces()

        fetchFromApi(context, call, onSuccess, onFail, loadingFinished)
    }

    fun getSpells(context: Context, onSuccess: (List<Spell>) -> Unit, onFail: () -> Unit, loadingFinished: () -> Unit) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(context.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: ApiService = retrofit.create(ApiService::class.java)

        val call: Call<List<Spell>> = service.getSpells()

        fetchFromApi(context, call, onSuccess, onFail, loadingFinished)
    }


}