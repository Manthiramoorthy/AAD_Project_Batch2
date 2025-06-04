package com.example.myaapp.note_app.ui

import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract.Constants
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myaapp.R
import com.example.myaapp.databinding.ActivityNoteListBinding
import com.example.myaapp.note_app.adapter.NotesAdapter
import com.example.myaapp.note_app.common.Constant
import com.example.myaapp.note_app.local_db.NoteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("NoteListActivity", "Oncreate")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
    }

    override fun onStart() {
        getDataFromDBUpdateUI()
        Log.d("DetailsActivity", "onStart")

        super.onStart()
    }

    private fun getDataFromDBUpdateUI() {
        lifecycleScope.launch(Dispatchers.IO) {
            val list = NoteDatabase.getInstance(this@NoteListActivity).noteDao().getAll()
            if (!list.isNullOrEmpty()) {
                val adapter = NotesAdapter(list)
                withContext(Dispatchers.Main) {
                    binding.recyclerViewNotes.adapter = adapter
                    binding.recyclerViewNotes.layoutManager =
                        LinearLayoutManager(
                            this@NoteListActivity,
                            LinearLayoutManager.VERTICAL,
                            false
                        )

                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@NoteListActivity, "Data not found", Toast.LENGTH_LONG)
                        .show()
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