package com.example.myaapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BroadCastReceiverActivity : AppCompatActivity() {
    private val receiver = MyBroadCastReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_broad_cast_receiver)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val filter = IntentFilter(Intent.ACTION_POWER_CONNECTED)
        registerReceiver(receiver, filter)

    }
}

class MyBroadCastReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Log.d("MyBroadCastReceiver", "MyBroadCastReceiver : " + p1?.action)
        Toast.makeText(p0, "MyBroadCastReceiver : " + p1?.action, Toast.LENGTH_SHORT).show()
    }
}