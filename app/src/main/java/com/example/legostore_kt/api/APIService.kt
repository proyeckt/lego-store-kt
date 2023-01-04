package com.example.legostore_kt.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface APIService {
    @GET("all-products")
    suspend fun getProducts() : Response<ProductsResponse>
    //fun getProducts() : Call<List<Product>>

    //@GET("detail/{id}")
    //fun getProductById(@Path("id") id: Int ): Call<Product>

    @POST("buy")
    suspend fun buy() : Response<ProductsResponse>

}