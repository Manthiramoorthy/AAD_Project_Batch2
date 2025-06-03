package com.example.myaapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myaapp.databinding.ActivityAppPermissionBinding

class AppPermissionActivity : AppCompatActivity() {
    lateinit var binding: ActivityAppPermissionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAppPermissionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.webView.loadUrl("https://manthiramoorthy.github.io/AndroidXplore/")

        binding.callButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED
            ) {
                makeCall()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    101
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
//        makeCall()
        Log.d("AppPermissionActivity", "onRequestPermissionsResult")
        if (requestCode == 101) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                makeCall()
            } else {
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_LONG).show()
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun makeCall() {
        Log.d("AppPermissionActivity", "makeCall")
        val intent = Intent(Intent.ACTION_CALL, ("tel:" + binding.editTextPhoneNumber.text).toUri())
        startActivity(intent)
    }
}