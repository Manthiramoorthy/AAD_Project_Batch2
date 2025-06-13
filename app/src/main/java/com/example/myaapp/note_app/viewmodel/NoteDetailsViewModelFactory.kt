package com.example.myaapp.note_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myaapp.note_app.local_db.NoteDao

class NoteDetailsViewModelFactory(
    private val noteDao: NoteDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteDetailsViewModel(noteDao) as T
    }
}