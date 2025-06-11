package com.example.myaapp.musicapp

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.myaapp.R

class MusicForegroundService : Service() {

    override fun onBind(intent: Intent): IBinder? = null
    lateinit var mediaPlayer: MediaPlayer

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == "ACTION_TOGGLE_PLAY") {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
            } else {
                mediaPlayer.start()
            }
//            // Update notification to reflect play/pause
            val notification = createNotification()
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(10, notification)
            return START_STICKY

        } else {
            mediaPlayer = MediaPlayer.create(this, R.raw.sample)
            mediaPlayer.start()

            val notification = createNotification()
            startForeground(10, notification)
            return START_NOT_STICKY
        }

    }

    override fun onDestroy() {
        mediaPlayer.stop()
        mediaPlayer.release()
        super.onDestroy()
    }

    private fun createNotification(): Notification {

        val pauseIntent =
            Intent(this, MusicForegroundService::class.java)
        pauseIntent.action = "ACTION_TOGGLE_PLAY"
        val pausePendingIntent = PendingIntent.getService(
            this, 0, pauseIntent, PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, "music_channel")
            .setContentTitle("Playing Music")
            .setSmallIcon(R.drawable.play_icon)
            .addAction(
                R.drawable.play_icon,
                if (mediaPlayer.isPlaying) "Pause" else "Play",
                pausePendingIntent
            )
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "music_channel",
                "Music Playback",
                NotificationManager.IMPORTANCE_LOW
            )
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
    }

}