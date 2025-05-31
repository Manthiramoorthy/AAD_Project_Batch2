package com.example.myaapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myaapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginButton.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()
            if (username.equals("Moorthy") && password.equals("12345")) {
                val intent = Intent(this, ListViewActivity::class.java)
                intent.putExtra("username", username)
                startActivity(intent)
                Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Invalid credentails", Toast.LENGTH_LONG).show()
            }
        }
    }
}