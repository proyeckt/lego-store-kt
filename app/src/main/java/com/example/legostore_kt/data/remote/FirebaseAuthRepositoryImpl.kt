package com.example.legostore_kt.data.remote

import com.example.legostore_kt.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
    ) : AuthRepository {



    override suspend fun login(email: String, password: String): Boolean {
        return try{
            var isSuccessful: Boolean = false
            firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener { isSuccessful = true }
                .addOnFailureListener { isSuccessful = false }
                .await()
            isSuccessful //Value being returned with the keyword return before try
        } catch (e: Exception){
            println(e)
            false
        }
    }

    override suspend fun register(email: String, password: String): Boolean {
        return try{
            var isSuccessful: Boolean = false

            firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener { isSuccessful = it.isSuccessful }
                .await()
            isSuccessful
        }
        catch (e: Exception){
            println(e)
            false
        }
    }

}

