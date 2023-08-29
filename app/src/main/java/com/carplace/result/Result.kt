package com.carplace.result

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
}

fun<T> Result<T>.handle(onError: (Result.Error) -> Unit = { }, onSuccess: (Result.Success<T>) -> Unit) {
    when (this) {
        is Result.Success -> {
            onSuccess(this)
        }
        is Result.Error -> {
            onError(this)
        }
    }
}