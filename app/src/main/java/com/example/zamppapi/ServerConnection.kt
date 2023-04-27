package com.example.zamppapi

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//step 1 = connection with server
object ServerConnection {
    var retrofitInstance:Retrofit?=null

    @JvmStatic
    fun getConnection(): Retrofit? {
        if(retrofitInstance==null)
        {
           val okHttpClint=OkHttpClient.Builder().build()
            val gson= GsonBuilder().create()
            retrofitInstance=Retrofit.Builder().baseUrl("http://192.168.56.1/carss/")
                .client(okHttpClint).addConverterFactory(GsonConverterFactory.create(gson)).build()
        }
        return retrofitInstance
    }
}