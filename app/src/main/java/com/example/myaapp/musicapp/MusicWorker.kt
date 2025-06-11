package com.example.myaapp.musicapp

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

private val LOG_TAG = MusicWorker::class.simpleName

class MusicWorker(private val context: Context, params: WorkerParameters) :
    Worker(context, params) {
    override fun doWork(): Result {
        Log.d(LOG_TAG, "doWork")
        val intent = Intent(context, MusicForegroundService::class.java)
        ContextCompat.startForegroundService(context, intent)
        return Result.success()
    }
}