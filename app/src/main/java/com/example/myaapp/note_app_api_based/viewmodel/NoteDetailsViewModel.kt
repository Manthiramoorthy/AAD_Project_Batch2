package com.example.myaapp.note_app_api_based.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.myaapp.note_app_api_based.api.ApiRepository
import com.example.myaapp.note_app_api_based.api.NoteApi
import com.example.myaapp.note_app_api_based.common.ResultWrapper
import com.example.myaapp.note_app_api_based.common.Utility
import com.example.myaapp.note_app_api_based.ui.NoteDetailsApiActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteDetailsViewModel : ViewModel() {
    val apiSuccessResult = MutableLiveData<String>()
    val apiFailureResult = MutableLiveData<String>()
    fun insert(context: Context, note: NoteApi) {
        viewModelScope.launch(Dispatchers.IO) {
            val apiRepository = ApiRepository(context)
            val result = Utility.safeApiCall {
                apiRepository.apiService.createNote(note)
            }
            when (result) {
                is ResultWrapper.Success -> {
                    apiSuccessResult.postValue("Created")
                }

                is ResultWrapper.Failure -> {
                    apiFailureResult.postValue(result.message)
                }
            }

        }
    }

    fun update(context: Context, note: NoteApi, id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val apiRepository = ApiRepository(context = context)
            val result = Utility.safeApiCall {
                apiRepository.apiService.updateNote(id, note)
            }
            when (result) {
                is ResultWrapper.Success -> {
                    apiSuccessResult.postValue("Updated")
                }

                is ResultWrapper.Failure -> {
                    apiFailureResult.postValue(result.message)
                }
            }
        }
    }
}