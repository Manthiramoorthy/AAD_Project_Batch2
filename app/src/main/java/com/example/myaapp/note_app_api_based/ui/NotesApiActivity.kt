package com.example.myaapp.note_app_api_based.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myaapp.R
import com.example.myaapp.databinding.ActivityNotesApiBinding
import com.example.myaapp.note_app.common.Constant
import com.example.myaapp.note_app_api_based.NotesApiAdapter
import com.example.myaapp.note_app_api_based.viewmodel.NotesApiViewModel


class NotesApiActivity : AppCompatActivity() {
    private val LOG_TAG = NotesApiActivity::class.java.simpleName
    lateinit var binding: ActivityNotesApiBinding

    val viewModel: NotesApiViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNotesApiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.createButton.setOnClickListener {
            val intent = Intent(this, NoteDetailsApiActivity::class.java)
            intent.putExtra(Constant.SOURCE_KEY, Constant.CREATE_VALUE)
            startActivity(intent)
        }

        binding.refreshButton.setOnClickListener {
            Log.d(LOG_TAG, "refreshButton")
            viewModel.getNoteAndUpdateUI(this)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.INVISIBLE
            }
        }

        viewModel.notesResult.observe(this) { list ->
            val adapter = NotesApiAdapter(list)
            binding.recyclerViewNotes.adapter = adapter
            binding.recyclerViewNotes.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )
        }

        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onStart() {
        Log.d("DetailsActivity", "onStart") // Main
        viewModel.getNoteAndUpdateUI(this)
        super.onStart() // Main
    }


}