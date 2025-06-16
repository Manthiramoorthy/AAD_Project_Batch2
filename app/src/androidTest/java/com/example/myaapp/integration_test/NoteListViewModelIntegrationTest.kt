package com.example.myaapp.integration_test

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myaapp.note_app.local_db.Note
import com.example.myaapp.note_app.local_db.NoteDao
import com.example.myaapp.note_app.local_db.NoteDatabase
import com.example.myaapp.note_app.viewmodel.NoteListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NoteListViewModelIntegrationTest {
    lateinit var viewModel: NoteListViewModel
    lateinit var noteDao: NoteDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val database = Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        noteDao = database.noteDao()
        viewModel = NoteListViewModel(noteDao, Dispatchers.Main)

    }

    @Test
    fun InsertTheDataThenWillCallGetAllMethod() = runTest {
        noteDao.insert(
            Note(
                title = "title",
                content = "content"
            )
        )
        viewModel.getDataFromDBUpdateUI()
        advanceUntilIdle()
        Thread.sleep(1000)
        Assert.assertTrue(viewModel.successData.value?.size == 1)
    }

    @Test
    fun CallGetAllMethodWithoutInsertingAnyValue() = runTest {
        viewModel.getDataFromDBUpdateUI()
        advanceUntilIdle()
        Thread.sleep(1000)
        Assert.assertEquals(viewModel.failureMessage.value, "No Data Found")
    }

}