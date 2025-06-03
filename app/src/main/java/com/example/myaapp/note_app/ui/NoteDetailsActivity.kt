package com.example.myaapp.note_app.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myaapp.R
import com.example.myaapp.databinding.ActivityNoteDetailsBinding
import com.example.myaapp.note_app.common.Constant.CONTENT_KEY
import com.example.myaapp.note_app.common.Constant.LIST_ITEM_VALUE
import com.example.myaapp.note_app.common.Constant.SOURCE_KEY
import com.example.myaapp.note_app.common.Constant.TITLE_KEY

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

    }
}