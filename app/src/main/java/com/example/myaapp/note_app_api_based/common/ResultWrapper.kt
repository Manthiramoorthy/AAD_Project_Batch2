package com.example.myaapp.note_app_api_based.common

sealed class ResultWrapper<out T> {
    data class Success<T>(val data: T) : ResultWrapper<T>()
    data class Failure(val message: String) : ResultWrapper<Nothing>()
}