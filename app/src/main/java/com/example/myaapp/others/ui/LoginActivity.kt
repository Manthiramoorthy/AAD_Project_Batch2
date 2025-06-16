package com.example.myaapp.others.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myaapp.databinding.ActivityLoginBinding
import com.example.myaapp.others.common.Constants

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPref = getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE)

        val isLoggedIn = sharedPref.getBoolean(Constants.IS_LOGGED_IN_KEY, false)
        if (isLoggedIn) {
            val username = sharedPref.getString(Constants.USERNAME_KEY, "")
            val intent = Intent(this, ListViewActivity::class.java)
            intent.putExtra(Constants.USERNAME_KEY, username)
            startActivity(intent)
        }


        binding.loginButton.setOnClickListener {


            val username = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()
            if (username.length > 7 && password.length > 7) {
                val intent = Intent(this, ListViewActivity::class.java)
                intent.putExtra(Constants.USERNAME_KEY, username)


                val editor = sharedPref.edit()
                editor.putBoolean(Constants.IS_LOGGED_IN_KEY, true)
                editor.putString(Constants.USERNAME_KEY, username)
                editor.apply()

                startActivity(intent)
                Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
            } else {
                binding.errorMessage.visibility = View.VISIBLE
                binding.errorMessage.text = "Invalid credentials"
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_LONG).show()
            }
        }
    }
}