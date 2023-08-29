package com.carplace.data.repository

import com.carplace.result.Result
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): Flow<Result<AuthResult>>

    suspend fun signUp(email: String, password: String): Flow<Result<AuthResult>>

    fun signOut()

    fun getCurrentUser() : FirebaseUser?
}