package com.example.myaapp.note_app_api_based.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.myaapp.R
import com.example.myaapp.databinding.ActivityNoteDetailsBinding
import com.example.myaapp.note_app.common.Constant
import com.example.myaapp.note_app_api_based.api.ApiRepository
import com.example.myaapp.note_app_api_based.api.NoteApi
import com.example.myaapp.note_app_api_based.common.ResultWrapper
import com.example.myaapp.note_app_api_based.common.Utility
import com.example.myaapp.note_app_api_based.viewmodel.NoteDetailsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteDetailsApiActivity : AppCompatActivity() {
    val viewModel: NoteDetailsViewModel by viewModels()
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
                viewModel.update(
                    context = this,
                    note = note,
                    id = id.orEmpty()
                )
            } else {
                viewModel.insert(
                    context = this,
                    note = note
                )
            }
        }

        viewModel.apiFailureResult.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }

        viewModel.apiSuccessResult.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            onBackPressedDispatcher.onBackPressed()
        }
    }

}