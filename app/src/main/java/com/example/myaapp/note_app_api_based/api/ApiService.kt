package com.example.myaapp.note_app_api_based.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("notes")
    suspend fun getAll(): List<NoteApi>?

    @POST("notes")
    suspend fun createNote(
        @Body
        noteApi: NoteApi
    ): NoteApi?

    @PUT("notes/{id}")
    suspend fun updateNote(
        @Path("id")
        id: String,
        @Body
        noteApi: NoteApi
    ): NoteApi?
}