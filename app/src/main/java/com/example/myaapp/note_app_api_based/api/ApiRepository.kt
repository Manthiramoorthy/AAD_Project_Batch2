package com.example.myaapp.note_app_api_based.api

import android.util.Log
import android.widget.Toast
import com.example.myaapp.note_app_api_based.NotesApiActivity
import com.example.myaapp.note_app_api_based.common.Constant
import com.google.gson.JsonParseException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object ApiRepository {
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constant.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(ApiService::class.java)

    suspend fun <T> safeApiCall(
        api: suspend () -> T
    ): ResultWrapper<T> {
        try {
            val response = api.invoke()
            return ResultWrapper.Success(response)
        } catch (e: JsonParseException) {
            return ResultWrapper.Failure("Invalid repose")
        } catch (e: IOException) {
            return ResultWrapper.Failure("Network error")
        } catch (e: Exception) {
            return ResultWrapper.Failure("Unknown error")
        }
    }
}

sealed class ResultWrapper<out T> {
    data class Success<T>(val data: T) : ResultWrapper<T>()
    data class Failure(val message: String) : ResultWrapper<Nothing>()
}