package com.example.myaapp.musicapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import com.example.myaapp.R
import com.example.myaapp.databinding.ActivityMusicPlayerBinding
import java.util.concurrent.TimeUnit

class MusicPlayerActivity : AppCompatActivity() {

    lateinit var serviceIntent: Intent

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMusicPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val intent = Intent(this, MusicService::class.java)
        serviceIntent = Intent(this, MusicForegroundService::class.java)
        binding.startButton.setOnClickListener {
//            startService(intent)

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED
            ) {
                ContextCompat.startForegroundService(this, serviceIntent)
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    10
                )
            }
        }

        binding.stopButton.setOnClickListener {
//            stopService(intent)
            stopService(serviceIntent)
        }
        val constraint = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiresBatteryNotLow(true)
            .build()
        val timeInMillis = calculateBetweenTimeInMillis(7, 20)
        val request = OneTimeWorkRequestBuilder<MusicWorker>()
            .setInitialDelay(timeInMillis, TimeUnit.MILLISECONDS)
            .setConstraints(constraint)
            .build()

        binding.scheduleButton.setOnClickListener {
            WorkManager.getInstance(this).enqueue(request)
        }

        binding.cancelScheduleButton.setOnClickListener {
            WorkManager.getInstance(this).cancelWorkById(request.id)
            val intent = Intent(this, MusicForegroundService::class.java)
            stopService(intent)
        }

        binding.perioticScheduleButton.setOnClickListener {
            val request = PeriodicWorkRequestBuilder<MusicWorker>(14, TimeUnit.MINUTES)
                .setConstraints(constraint)
                .build()
            WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                "music_worker",
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }
    }

    fun calculateBetweenTimeInMillis(hour: Int, minute: Int): Long {
        val calendar = Calendar.getInstance()
        val alarmCalendar = Calendar.getInstance().apply {
            set(Calendar.HOUR, hour)
            set(Calendar.MINUTE, minute)
        }
        return alarmCalendar.timeInMillis - calendar.timeInMillis
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 10 && grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
            ContextCompat.startForegroundService(this, serviceIntent)
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}