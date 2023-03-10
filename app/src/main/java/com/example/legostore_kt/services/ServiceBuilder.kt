package com.example.legostore_kt.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private const val URL ="https://1be9db56-c889-466d-9c12-cba178414901.mock.pstmn.io/"

    //CREATE HTTP CLIENT
    private val okHttp =OkHttpClient.Builder()

    //retrofit builder
    private val builder =Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    //create retrofit Instance
    private val retrofit = builder.build()

    //we will use this class to create an anonymous inner class function that
    //implements API service Interface

    fun <T> buildService (serviceType :Class<T>):T{
        return retrofit.create(serviceType)
    }

}