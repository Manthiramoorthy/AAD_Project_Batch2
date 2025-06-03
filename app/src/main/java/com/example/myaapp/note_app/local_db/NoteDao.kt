package com.example.myaapp.note_app.local_db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao // Data access object
interface NoteDao {
    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Query("select * from notes")
    fun getAll(): List<Note>?

    @Delete
    fun delete(note: Note)
}