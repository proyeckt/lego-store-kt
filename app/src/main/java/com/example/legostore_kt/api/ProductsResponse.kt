package com.example.legostore_kt.api

import com.example.legostore_kt.domain.model.Product
import com.google.gson.annotations.SerializedName

data class ProductsResponse(
    @SerializedName ("products") val products: List<Product>
)
