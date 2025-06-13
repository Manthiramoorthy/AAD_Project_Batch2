package com.example.myaapp.note_app.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myaapp.R
import com.example.myaapp.databinding.ActivityNoteDetailsBinding
import com.example.myaapp.note_app.common.Constant
import com.example.myaapp.note_app.common.Constant.CONTENT_KEY
import com.example.myaapp.note_app.common.Constant.ID_KEY
import com.example.myaapp.note_app.common.Constant.LIST_ITEM_VALUE
import com.example.myaapp.note_app.common.Constant.SOURCE_KEY
import com.example.myaapp.note_app.common.Constant.TITLE_KEY
import com.example.myaapp.note_app.local_db.Note
import com.example.myaapp.note_app.local_db.NoteDao
import com.example.myaapp.note_app.local_db.NoteDatabase
import com.example.myaapp.note_app.viewmodel.NoteDetailsViewModel
import com.example.myaapp.note_app.viewmodel.NoteDetailsViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityNoteDetailsBinding
    lateinit var viewModel: NoteDetailsViewModel


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 102 && resultCode == RESULT_OK) {
            val uri = data?.data
            if (uri != null) {
                val text = readTextFromUri(uri)
                binding.contentText.setText(text)
            }
        }
    }

    private fun readTextFromUri(uri: Uri): String {
        val inputStream = contentResolver.openInputStream(uri)
        return inputStream?.bufferedReader().use { it?.readText() ?: "" }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        val noteDao = NoteDatabase.getInstance(this).noteDao()
        val factory = NoteDetailsViewModelFactory(noteDao)
        viewModel = ViewModelProvider(this, factory)[NoteDetailsViewModel::class.java]

        binding = ActivityNoteDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val source = intent.getStringExtra(SOURCE_KEY)
        if (source == LIST_ITEM_VALUE) {
            val title = intent.getStringExtra(TITLE_KEY)
            val content = intent.getStringExtra(CONTENT_KEY)
            binding.titleText.setText(title)
            binding.contentText.setText(content)
        } else {
            binding.saveButton.text = "Create"
        }


        binding.attachButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "text/plain"
            startActivityForResult(intent, 102)
        }


        binding.deleteButton.setOnClickListener {
            val id = intent.getIntExtra(ID_KEY, 0)
            viewModel.deleteNote(id)
        }

        binding.saveButton.setOnClickListener {
            if (source == Constant.CREATE_VALUE) {
                val title = binding.titleText.text.toString()
                val content = binding.contentText.text.toString()
                viewModel.createNote(title, content)
            } else {
                val title = binding.titleText.text.toString()
                val content = binding.contentText.text.toString()
                val id = intent.getIntExtra(ID_KEY, 0)
                viewModel.updateNote(id, title, content)
            }
        }

        viewModel.successMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            onBackPressedDispatcher.onBackPressed()
        }

        viewModel.failureMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }

    }
}