package com.example.myaapp.note_app_api_based

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myaapp.R
import com.example.myaapp.databinding.ActivityNotesApiBinding
import com.example.myaapp.note_app_api_based.api.ApiRepository
import com.example.myaapp.note_app_api_based.api.ResultWrapper
import com.google.gson.JsonParseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class NotesApiActivity : AppCompatActivity() {
    lateinit var binding: ActivityNotesApiBinding
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
            startActivity(intent)
        }


    }

    override fun onStart() {
        Log.d("DetailsActivity", "onStart") // Main
        lifecycleScope.launch {
            val result = lifecycleScope.async(Dispatchers.IO) {
                ApiRepository.safeApiCall {
                    ApiRepository.apiService.getAll()
                }
            }.await()
            when (result) {
                is ResultWrapper.Success -> {
                    if (result.data != null)
                        binding.recyclerViewNotes.adapter = NotesApiAdapter(result.data) // Main
                        binding.recyclerViewNotes.layoutManager = LinearLayoutManager(
                            this@NotesApiActivity,
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                }

                is ResultWrapper.Failure -> {
                    Toast.makeText(this@NotesApiActivity, result.message, Toast.LENGTH_LONG).show()
                    }
                }
        }
        super.onStart() // Main
    }

    override fun onResume() {
        Log.d("DetailsActivity", "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d("DetailsActivity", "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d("DetailsActivity", "onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d("DetailsActivity", "onDestroy")
        super.onDestroy()
    }

    override fun onRestart() {
        Log.d("DetailsActivity", "onRestart")
        super.onRestart()
    }

}