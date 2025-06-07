package com.example.myaapp.note_app_api_based

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myaapp.R
import com.example.myaapp.databinding.ActivityNotesApiBinding
import com.example.myaapp.note_app_api_based.api.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        Log.d("DetailsActivity", "onStart")
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val list = ApiRepository.apiService.getAll()
                if (list != null) {
                    withContext(Dispatchers.Main) {
                        binding.recyclerViewNotes.adapter = NotesApiAdapter(list)
                        binding.recyclerViewNotes.layoutManager = LinearLayoutManager(
                            this@NotesApiActivity,
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                    }
                }
            } catch (e: Exception) {
                Log.d("NotesApiActivity", e.stackTraceToString())
                e.printStackTrace()
            }
        }
        super.onStart()
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