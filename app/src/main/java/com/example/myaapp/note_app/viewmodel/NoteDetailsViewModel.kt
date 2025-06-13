package com.example.myaapp.note_app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myaapp.note_app.local_db.Note
import com.example.myaapp.note_app.local_db.NoteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteDetailsViewModel(
    private val noteDao: NoteDao
) : ViewModel() {
    val successMessage = MutableLiveData<String>()
    val failureMessage = MutableLiveData<String>()
    fun createNote(title: String, content: String) {
        val note = Note(
            title = title,
            content = content
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                noteDao.insert(note)
                successMessage.postValue("Created")
            } catch (e: Exception) {
                failureMessage.postValue("Unable to create" + e.message)
            }
        }
    }

    fun updateNote(id: Int, title: String, content: String) {
        val note = Note(
            id = id,
            title = title,
            content = content
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                noteDao.update(note)
                successMessage.postValue("Updated")
            } catch (e: Exception) {
                failureMessage.postValue("Unable to update" + e.message)
            }
        }
    }

    fun deleteNote(id: Int) {
        val note = Note(
            id = id,
            title = "",
            content = ""
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                noteDao.delete(note)
                successMessage.postValue("Deleted")
            } catch (e: Exception) {
                failureMessage.postValue("Unable to delete" + e.message)
            }
        }
    }
}