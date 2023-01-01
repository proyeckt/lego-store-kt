package com.example.legostore_kt.presentation.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.legostore_kt.domain.usecase.FirebaseProductUseCase
import com.example.legostore_kt.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productUseCase: FirebaseProductUseCase
): ViewModel() {
    private val _homeState: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    val homeState: LiveData<Resource<Boolean>> get() = _homeState

    /*
    fun why(email: String, password: String) {
        viewModelScope.launch {
            homeUseCase(email,password).onEach { state ->
                _homeState.value = state
            }.launchIn(viewModelScope)
        }
    }
     */
}