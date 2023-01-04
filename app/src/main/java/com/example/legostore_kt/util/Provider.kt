package com.example.legostore_kt.util

import com.example.legostore_kt.domain.model.Product

class Provider {

    companion object {
        var productsList: MutableList<Product> = mutableListOf<Product>()
        var cartList: MutableList<Product> = mutableListOf<Product>()
    }

}