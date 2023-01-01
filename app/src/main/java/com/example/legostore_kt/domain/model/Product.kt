package com.example.legostore_kt.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int,
    val name: String,
    val unit_price: Int,
    val stock: Int,
    val image: String
) : Parcelable
