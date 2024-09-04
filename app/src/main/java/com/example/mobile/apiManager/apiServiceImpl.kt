package com.example.mobile.apiManager

import android.content.Context
import android.widget.Toast
import com.example.mobile.R
import retrofit.Call
import retrofit.Callback
import retrofit.GsonConverterFactory
import retrofit.Response
import retrofit.Retrofit
import javax.inject.Inject

class ApiServiceImpl @Inject constructor() {

    fun getHitDie(className: String, context: Context, onSuccess: (Int) -> Unit, onFail: () -> Unit, loadingFinished: () -> Unit) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(
                context.getString(R.string.base_url)
            )
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()

        val service: ApiService = retrofit.create(ApiService::class.java)

        val call: Call<Int> = service.getHitDice(className)

        call.enqueue(object : Callback<Int> {
            override fun onResponse(response: Response<Int>?, retrofit: Retrofit?) {
                loadingFinished()
                if(response?.isSuccess == true) {
                    val hitDice: Int = response.body()
                    onSuccess(hitDice)
                } else {
                    onFailure(Exception("Bad request"))
                }
            }

            override fun onFailure(t: Throwable?) {
                Toast.makeText(context, "Can't get hit dice", Toast.LENGTH_SHORT).show()
                onFail()
                loadingFinished()
            }
        })
    }
}