package com.example.myaapp.note_app.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
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


        val source = intent.getStringExtra(SOURCE_KEY)
        if (source == LIST_ITEM_VALUE) {
            val title = intent.getStringExtra(TITLE_KEY)
            val content = intent.getStringExtra(CONTENT_KEY)
            binding.titleText.setText(title)
            binding.contentText.setText(content)
        } else {
            binding.saveButton.text = "Create"
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
                    }
                }
            }
        }

    }
}