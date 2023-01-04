package com.example.legostore_kt.domain.usecase

import com.example.legostore_kt.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CartUseCase @Inject constructor(
) {
    suspend operator fun invoke(cartList: MutableList<Product>): Flow<Int> = flow {
        val total: Int = cartList.sumOf { it.unit_price }
        emit (total)
    }
}