package com.example.mobile.apiManager

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.mobile.R
import com.example.mobile.model.item.Items
import retrofit.Call
import retrofit.Callback
import retrofit.GsonConverterFactory
import retrofit.Response
import retrofit.Retrofit
import javax.inject.Inject

class ApiServiceImpl @Inject constructor() {

    fun getItems(context: Context, onSuccess: (List<Items>) -> Unit, onFail: () -> Unit, loadingFinished: () -> Unit) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(context.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: ApiService = retrofit.create(ApiService::class.java)

        val call: Call<List<Items>> = service.getItems()

        call.enqueue(object : Callback<List<Items>> {

            override fun onResponse(response: Response<List<Items>>?, retrofit: Retrofit?) {
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

}