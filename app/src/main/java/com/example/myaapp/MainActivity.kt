package com.example.myaapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.loginButton)
        val textGreeting = findViewById<TextView>(R.id.textGreeting)
        val usernameEditText = findViewById<EditText>(R.id.editTextUsername)
        val passwordEditText = findViewById<EditText>(R.id.editTextPassword)

        val imageView = findViewById<ImageView>(R.id.imageView)

        button.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            if (username.length > 7 && password.length > 7) {
                imageView.setImageResource(R.drawable.success)
                textGreeting.setText("Hi " + username)
                textGreeting.setBackgroundColor(Color.GREEN)
                Toast.makeText(this, "Hi " + username, Toast.LENGTH_LONG).show()
            } else {
                imageView.setImageResource(R.drawable.error)
                textGreeting.setText("Invalid credentials")
                textGreeting.setBackgroundColor(Color.RED)
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_LONG).show()
            }
            Log.d("MainActivity", "Button is clicked")

        }
    }
}