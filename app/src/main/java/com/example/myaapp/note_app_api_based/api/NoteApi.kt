package com.example.myaapp.note_app_api_based.api

data class NoteApi(
    val content: String,
    val id: String? = null,
    val title: String
)