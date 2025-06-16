package com.example.myaapp.note_app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myaapp.note_app.local_db.Note
import com.example.myaapp.note_app.local_db.NoteDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteListViewModel(
    private val noteDao: NoteDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    val successData = MutableLiveData<List<Note>>()
    val failureMessage = MutableLiveData<String>()

    fun getDataFromDBUpdateUI() {
        viewModelScope.launch(dispatcher) {
            try {
                val list = noteDao.getAll()
                if (!list.isNullOrEmpty()) {
                    successData.postValue(list)
                } else {
                    failureMessage.postValue("No Data Found")
                }
            } catch (e: Exception) {
                failureMessage.postValue("Error found :" + e.message)
            }

        }
    }
}