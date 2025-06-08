package com.example.myaapp.note_app_api_based.common

import com.google.gson.JsonParseException
import java.io.IOException

object Utility {
    suspend fun <T> safeApiCall(
        api: suspend () -> T
    ): ResultWrapper<T> {
        try {
            val response = api.invoke()
            return ResultWrapper.Success(response)
        } catch (e: JsonParseException) {
            return ResultWrapper.Failure("Invalid reponse")
        } catch (e: IOException) {
            return ResultWrapper.Failure("Network error")
        } catch (e: Exception) {
            return ResultWrapper.Failure("Unknown error")
        }
    }
}