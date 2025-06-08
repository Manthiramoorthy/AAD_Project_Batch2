package com.example.myaapp.note_app_api_based

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myaapp.R
import com.example.myaapp.databinding.ActivityNotesApiBinding
import com.example.myaapp.note_app.common.Constant
import com.example.myaapp.note_app_api_based.api.ApiRepository
import com.example.myaapp.note_app_api_based.common.ResultWrapper
import com.example.myaapp.note_app_api_based.common.Utility
import com.google.gson.JsonParseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException


class NotesApiActivity : AppCompatActivity() {
    private val LOG_TAG = NotesApiActivity::class.java.simpleName
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
            intent.putExtra(Constant.SOURCE_KEY, Constant.CREATE_VALUE)
            startActivity(intent)
        }

        binding.refreshButton.setOnClickListener {
            Log.d(LOG_TAG, "refreshButton")
            getNoteAndUpdateUI()
        }


    }

    override fun onStart() {
        Log.d("DetailsActivity", "onStart") // Main
        getNoteAndUpdateUI()
        super.onStart() // Main
    }

    fun getNoteAndUpdateUI() {
        lifecycleScope.launch {
            binding.progressBar.visibility = View.VISIBLE
            val apiRepository = ApiRepository(this@NotesApiActivity)
            val result = lifecycleScope.async(Dispatchers.IO) {
                Utility.safeApiCall {
                    apiRepository.apiService.getAll()
                }
            }.await()
            when (result) {
                is ResultWrapper.Success -> {
                    Log.d(LOG_TAG, "Result :" + result.data)
                    if (result.data != null)
                        binding.recyclerViewNotes.adapter = NotesApiAdapter(result.data) // Main
                    binding.recyclerViewNotes.layoutManager = LinearLayoutManager(
                        this@NotesApiActivity,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    binding.progressBar.visibility = View.GONE
                }

                is ResultWrapper.Failure -> {
                    Toast.makeText(this@NotesApiActivity, result.message, Toast.LENGTH_LONG).show()
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
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