package com.example.myaapp.note_app.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myaapp.R
import com.example.myaapp.databinding.ActivityNoteListBinding
import com.example.myaapp.note_app.adapter.NotesAdapter
import com.example.myaapp.note_app.common.Constant
import com.example.myaapp.note_app.local_db.NoteDatabase
import com.example.myaapp.note_app.viewmodel.NoteListViewModel
import com.example.myaapp.note_app.viewmodel.NoteListViewModelFactory

class NoteListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteListBinding
    lateinit var viewModel: NoteListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("NoteListActivity", "Oncreate")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val noteDao = NoteDatabase.getInstance(this).noteDao()
        val factory = NoteListViewModelFactory(noteDao)

        viewModel = ViewModelProvider(this, factory)[NoteListViewModel::class.java]

        binding = ActivityNoteListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding.createButton.setOnClickListener {
            val intent = Intent(this@NoteListActivity, NoteDetailsActivity::class.java)
            intent.putExtra(Constant.SOURCE_KEY, Constant.CREATE_VALUE)
            startActivity(intent)
        }

        viewModel.successData.observe(this) { list ->
            val adapter = NotesAdapter(list)
            binding.recyclerViewNotes.adapter = adapter
            binding.recyclerViewNotes.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }

        viewModel.failureMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onStart() {
        viewModel.getDataFromDBUpdateUI()
        Log.d("DetailsActivity", "onStart")

        super.onStart()
    }


}