package com.example.legostore_kt.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.legostore_kt.domain.usecase.FirebaseHomeUseCase
import com.example.legostore_kt.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCase: FirebaseHomeUseCase
): ViewModel() {
    private val _homeState: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    val homeState: LiveData<Resource<Boolean>> get() = _homeState

    fun why(email: String, password: String) {
        viewModelScope.launch {
            homeUseCase(email,password).onEach { state ->
                _homeState.value = state
            }.launchIn(viewModelScope)
        }
    }
}