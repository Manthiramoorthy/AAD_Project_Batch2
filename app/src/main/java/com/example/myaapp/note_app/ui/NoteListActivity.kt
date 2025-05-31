package com.example.myaapp.note_app.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myaapp.R
import com.example.myaapp.databinding.ActivityNoteListBinding
import com.example.myaapp.note_app.data.Note
import com.example.myaapp.note_app.data.NotesAdapter

class NoteListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityNoteListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val list = listOf(
            Note(
                title = "My notes 1",
                content = "My notes description"
            ),
            Note(
                title = "My notes 1",
                content = "My notes description"
            ),
            Note(
                title = "My notes 1",
                content = "My notes description"
            ),
            Note(
                title = "My notes 1",
                content = "My notes description"
            ),
            Note(
                title = "My notes 1",
                content = "My notes description"
            ),
            Note(
                title = "My notes 1",
                content = "My notes description"
            ),Note(
                title = "My notes 1",
                content = "My notes description"
            ),
            Note(
                title = "My notes 1",
                content = "My notes description"
            ),
            Note(
                title = "My notes 1",
                content = "My notes description"
            ),Note(
                title = "My notes 1",
                content = "My notes description"
            ),
            Note(
                title = "My notes 1",
                content = "My notes description"
            ),
            Note(
                title = "My notes 1",
                content = "My notes description"
            )
        )
        val adapter = NotesAdapter(list)
        binding.recyclerViewNotes.adapter = adapter
        binding.recyclerViewNotes.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}