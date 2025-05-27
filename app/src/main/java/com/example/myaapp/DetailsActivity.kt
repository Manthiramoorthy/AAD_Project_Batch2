package com.example.myaapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetailsActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("DetailsActivity", "onCreate")

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart() {
        Log.d("DetailsActivity", "onStart")

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