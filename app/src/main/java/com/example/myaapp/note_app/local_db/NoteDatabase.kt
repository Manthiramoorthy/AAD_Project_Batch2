package com.example.myaapp.note_app.local_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    // create abstract function which will return dao
    abstract fun noteDao(): NoteDao

    // create the NoteDatabase object
    companion object {
        private var INSTANCE: NoteDatabase? = null
        fun getInstance(context: Context): NoteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = context,
                    NoteDatabase::class.java,
                    "note_database"
                ).build() // create database if not exists, create tables if not exists, dao object
                INSTANCE = instance
                instance
            }
        }
    }
}


