package com.example.myaapp.note_app_api_based

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.myaapp.R
import com.example.myaapp.databinding.ActivityNoteDetailsBinding
import com.example.myaapp.databinding.ActivityNotesApiBinding
import com.example.myaapp.note_app_api_based.api.ApiRepository
import com.example.myaapp.note_app_api_based.api.NoteApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        binding.saveButton.setOnClickListener {
            val title = binding.titleText.text.toString()
            val content = binding.contentText.text.toString()
            val note = NoteApi(
                title = title,
                content = content
            )
            lifecycleScope.launch(Dispatchers.IO) {
                ApiRepository.apiService.createNote(note)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@NoteDetailsApiActivity, "Created", Toast.LENGTH_LONG).show()
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        }
    }
}