package com.example.myaapp.note_app.local_db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface NoteDao {
    @Insert
    fun insert(note: Note?)

    @Query("SELECT * FROM notes")
    fun getAll(): List<Note?>?

    @Delete
    fun delete(note: Note?)

    @Update
    fun update(note: Note?)
}