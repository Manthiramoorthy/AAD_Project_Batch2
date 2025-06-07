package com.example.myaapp.coroutines

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.myaapp.R
import com.example.myaapp.databinding.ActivityCoroutinesExampleBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoroutinesExampleActivity : AppCompatActivity() {
    companion object {
        private val LOG_TAG = CoroutinesExampleActivity::class.java.simpleName
    }

    lateinit var binding: ActivityCoroutinesExampleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCoroutinesExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.task1SyncButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
//                task1()
                val name = lifecycleScope.async { getUsername() }
                val reg = lifecycleScope.async { getRegNumber() }
                binding.task1Text.text = name.await() + " " + reg.await()
            }
        }
        binding.task2SyncButton.setOnClickListener {
            val coroutineScope = CoroutineScope(Dispatchers.IO)
            coroutineScope.launch {
                task2() // Main
            }


        }

        binding.bothSyncButton.setOnClickListener {
            val coroutineScope = CoroutineScope(Dispatchers.IO)
            coroutineScope.launch {
                task1()
                task2()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private suspend fun task1() {
        for (i in 0..100) {
            Log.d(LOG_TAG, "Task 1 - $i")
            withContext(Dispatchers.Main) {
                binding.task1Text.text = "Task 1 - $i"
            }
        }
    }

    suspend fun getUsername(): String {
        Log.d(LOG_TAG, "getUsername")
        delay(3000)
        Log.d(LOG_TAG, "getUsername completed")
        return "Moorthy"
    }

    suspend fun getRegNumber(): String {
        Log.d(LOG_TAG, "getRegNumber")
        delay(1000)
        Log.d(LOG_TAG, "getRegNumber completed")
        return "RE$@34423"
    }

    @SuppressLint("SetTextI18n")
    private suspend fun task2() {
        for (i in 0..100) {
            Log.d(LOG_TAG, "Task 2 - $i")
            delay(100)
            binding.task2Text.text = "task 2 - $i"
        }
    }

}