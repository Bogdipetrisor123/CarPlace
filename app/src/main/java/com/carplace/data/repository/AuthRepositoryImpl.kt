package com.carplace.data.repository

import com.carplace.result.Result
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resumeWithException

class AuthRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    AuthRepository {
    override suspend fun login(email: String, password: String): Flow<Result<AuthResult>> {
        return flow {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit(Result.Success(result))
        }.catch { error -> Result.Error(error) }
    }

    override suspend fun signUp(email: String, password: String): Flow<Result<AuthResult>> {
        return flow {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            emit(Result.Success(result))
        }.catch { error -> Result.Error(error) }
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }
}