package com.example.legostore_kt.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.legostore_kt.domain.usecase.FirebaseHomeUseCase
import com.example.legostore_kt.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCase: FirebaseHomeUseCase
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