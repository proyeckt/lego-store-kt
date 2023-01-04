package com.example.legostore_kt.presentation.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.legostore_kt.domain.model.Product
import com.example.legostore_kt.domain.usecase.CartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartUseCase: CartUseCase
): ViewModel() {
    private val _cartListState: MutableLiveData<Int> = MutableLiveData()
    val cartListState: LiveData<Int> get() = _cartListState

    fun calculateTotal(cartList:MutableList<Product>) {
        viewModelScope.launch {
            cartUseCase(cartList).onEach { state ->
                _cartListState.value = state
            }.launchIn(viewModelScope)
        }
    }

}