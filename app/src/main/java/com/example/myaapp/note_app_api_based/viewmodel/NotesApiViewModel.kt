package com.example.myaapp.note_app_api_based.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myaapp.note_app_api_based.api.ApiRepository
import com.example.myaapp.note_app_api_based.api.NoteApi
import com.example.myaapp.note_app_api_based.common.ResultWrapper
import com.example.myaapp.note_app_api_based.common.Utility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

val LOG_TAG = NotesApiViewModel::class.simpleName

class NotesApiViewModel : ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val notesResult = MutableLiveData<List<NoteApi>>()
    val errorMessage = MutableLiveData<String>()

    fun getNoteAndUpdateUI(context: Context) {
        viewModelScope.launch {
            isLoading.value = true
            val apiRepository = ApiRepository(context)
            val result = viewModelScope.async(Dispatchers.IO) {
                Utility.safeApiCall {
                    apiRepository.apiService.getAll()
                }
            }.await()
            when (result) {
                is ResultWrapper.Success -> {
                    notesResult.value = result.data
                }

                is ResultWrapper.Failure -> {
                    errorMessage.value = result.message
                }
            }
        }
    }
}