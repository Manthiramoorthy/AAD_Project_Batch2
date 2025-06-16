package com.example.myaapp.notes

import android.net.http.NetworkException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myaapp.note_app.local_db.Note
import com.example.myaapp.note_app.local_db.NoteDao
import com.example.myaapp.note_app.viewmodel.NoteListViewModel
import com.google.gson.JsonParseException
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test

class NoteListViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    val noteDao = mockk<NoteDao>()
    lateinit var viewModel: NoteListViewModel

    @Before
    fun setup() {
        viewModel = NoteListViewModel(noteDao, Dispatchers.Main)
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @Test
    fun `Given call getDataFromDBUpdateUI When getAll returns empty list Then failureMessage should be No Data Found`() =
        runTest {
            every { noteDao.getAll() } returns listOf()

            viewModel.getDataFromDBUpdateUI()
            advanceUntilIdle()

            Assert.assertEquals(viewModel.failureMessage.value, "No Data Found")
        }

    @Test
    fun `Given call getDataFromDBUpdateUI When getAll returns null Then failureMessage should be No Data Found`() =
        runTest {
            every { noteDao.getAll() } returns null

            viewModel.getDataFromDBUpdateUI()
            advanceUntilIdle()

            Assert.assertEquals(viewModel.failureMessage.value, "No Data Found")
        }

    @Test
    fun `Given call getDataFromDBUpdateUI When getAll returns valid list Then successData should be a valid list`() =
        runTest {
            val list = listOf(
                Note(
                    title = "title",
                    content = "content"
                )
            )
            every { noteDao.getAll() } returns list
            viewModel.getDataFromDBUpdateUI()
            advanceUntilIdle()

            Assert.assertEquals(list, viewModel.successData.value)
            Assert.assertEquals(null, viewModel.failureMessage.value)
        }

    @Test
    fun `Given call getDataFromDBUpdateUI When getAll returns exception Then failure message should contains Error found`() =
        runTest {
            every { noteDao.getAll() } throws JsonParseException("Invalid response")
            viewModel.getDataFromDBUpdateUI()
            advanceUntilIdle()

            Assert.assertTrue(viewModel.failureMessage.value?.contains("Error found") == true)
        }
}