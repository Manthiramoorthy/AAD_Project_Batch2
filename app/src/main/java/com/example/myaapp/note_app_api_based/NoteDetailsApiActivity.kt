package com.example.myaapp.note_app_api_based

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.myaapp.R
import com.example.myaapp.databinding.ActivityNoteDetailsBinding
import com.example.myaapp.databinding.ActivityNotesApiBinding
import com.example.myaapp.note_app.common.Constant
import com.example.myaapp.note_app.local_db.Note
import com.example.myaapp.note_app_api_based.NotesApiActivity
import com.example.myaapp.note_app_api_based.api.ApiRepository
import com.example.myaapp.note_app_api_based.api.NoteApi
import com.example.myaapp.note_app_api_based.common.ResultWrapper
import com.example.myaapp.note_app_api_based.common.Utility
import com.google.gson.JsonParseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class NoteDetailsApiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityNoteDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val source = intent.getStringExtra(Constant.SOURCE_KEY)
        if (source == Constant.LIST_ITEM_VALUE) {
            val title = intent.getStringExtra(Constant.TITLE_KEY)
            val content = intent.getStringExtra(Constant.CONTENT_KEY)
            binding.titleText.setText(title)
            binding.contentText.setText(content)
        }

        binding.saveButton.setOnClickListener {
            val title = binding.titleText.text.toString()
            val content = binding.contentText.text.toString()
            val id = intent.getStringExtra(Constant.ID_KEY)
            val note = NoteApi(
                title = title,
                content = content
            )
            if (source == Constant.LIST_ITEM_VALUE) {
                update(note = note, id = id.orEmpty())
            } else {
                insert(note)
            }
        }
    }

    fun insert(note: NoteApi) {
        lifecycleScope.launch(Dispatchers.IO) {
            val apiRepository = ApiRepository(this@NoteDetailsApiActivity)
            val result = Utility.safeApiCall {
                apiRepository.apiService.createNote(note)
            }
            when (result) {
                is ResultWrapper.Success -> {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@NoteDetailsApiActivity,
                            "Created",
                            Toast.LENGTH_LONG
                        ).show()
                        onBackPressedDispatcher.onBackPressed()
                    }
                }

                is ResultWrapper.Failure -> {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@NoteDetailsApiActivity,
                            result.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

        }
    }

    fun update(note: NoteApi, id: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val apiRepository = ApiRepository(this@NoteDetailsApiActivity)
            val result = Utility.safeApiCall {
                apiRepository.apiService.updateNote(id, note)
            }
            when (result) {
                is ResultWrapper.Success -> {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@NoteDetailsApiActivity,
                            "Updated",
                            Toast.LENGTH_LONG
                        ).show()
                        onBackPressedDispatcher.onBackPressed()
                    }
                }

                is ResultWrapper.Failure -> {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@NoteDetailsApiActivity,
                            result.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

        }
    }
}