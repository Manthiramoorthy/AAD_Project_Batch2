package com.example.myaapp.note_app.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
import com.example.myaapp.note_app.local_db.NoteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityNoteDetailsBinding
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
            val note = Note(
                id = id,
                title = "",
                content = ""
            )
            lifecycleScope.launch(Dispatchers.IO) {
                NoteDatabase.getInstance(this@NoteDetailsActivity).noteDao().delete(note)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@NoteDetailsActivity, "Note Delete", Toast.LENGTH_LONG)
                        .show()
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        }

        binding.saveButton.setOnClickListener {
            if (source == Constant.CREATE_VALUE) {
                val title = binding.titleText.text.toString()
                val content = binding.contentText.text.toString()
                val note = Note(
                    title = title,
                    content = content
                )
                lifecycleScope.launch(Dispatchers.IO) {
                    NoteDatabase.getInstance(this@NoteDetailsActivity).noteDao().insert(note)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@NoteDetailsActivity, "Note created", Toast.LENGTH_LONG)
                            .show()
                        onBackPressedDispatcher.onBackPressed()
                    }
                }
            } else {
                val title = binding.titleText.text.toString()
                val content = binding.contentText.text.toString()
                val id = intent.getIntExtra(ID_KEY, 0)
                val note = Note(
                    id = id,
                    title = title,
                    content = content
                )
                lifecycleScope.launch(Dispatchers.IO) {
                    NoteDatabase.getInstance(this@NoteDetailsActivity).noteDao().update(note)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@NoteDetailsActivity, "Note Updated", Toast.LENGTH_LONG)
                            .show()
                        onBackPressedDispatcher.onBackPressed()
                    }
                }
            }
        }

    }
}