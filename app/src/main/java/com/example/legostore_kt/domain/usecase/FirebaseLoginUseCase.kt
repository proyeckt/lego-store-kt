package com.example.legostore_kt.domain.usecase

import com.example.legostore_kt.domain.repository.AuthRepository
import com.example.legostore_kt.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FirebaseLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(email: String, password: String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading)
        val loginSuccessfully: Boolean = authRepository.login(email,password)

        if(loginSuccessfully){
            emit(Resource.Success(true))
        }
        else emit(Resource.Error("Error login user"))

        emit(Resource.Finished)
    }
}