package com.example.myaapp.others.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myaapp.R
import com.example.myaapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.openGoogleButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()
            if (username.length > 7 && password.length > 7) {
                val intent = Intent(this, RecyclerViewActivity::class.java)
                intent.putExtra("username", true)
                startActivity(intent)
            } else {
                binding.imageView.setImageResource(R.drawable.error)
                binding.textGreeting.setText("Invalid credentials")
                binding.textGreeting.setBackgroundColor(Color.RED)
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_LONG).show()
            }
            Log.d("MainActivity", "Button is clicked")

        }
    }
}