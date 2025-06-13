package com.example.myaapp.note_app_api_based.api

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import androidx.annotation.RequiresPermission
import com.example.myaapp.note_app_api_based.common.Constant
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


class ApiRepository(private val context: Context) {
    val cacheSize = (5 * 1024 * 1024).toLong() // 5 MB
    val cache = Cache(File(context.cacheDir, "http_cache"), cacheSize)
    val client = OkHttpClient.Builder()
        .cache(cache) // all the network response will be stored in cache
        .addInterceptor(offlineCacheInterceptor())
//        .addNetworkInterceptor(cacheInterceptor())
        .build()


    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(Constant.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(ApiService::class.java)

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun offlineCacheInterceptor() = Interceptor {
        var request = it.request() // get the data from the network
        if (!isNetworkAvailable()) {
            val cacheControl = CacheControl.Builder()
                .maxStale(7, TimeUnit.DAYS)
                .build()
            request = request.newBuilder()
                .cacheControl(cacheControl)
                .build() // get the data from cache
        }
        it.proceed(request)
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    @SuppressLint("ServiceCast")
    fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnected == true
    }

    fun cacheInterceptor() = Interceptor {
        val response = it.proceed(it.request())
        val cacheControl = CacheControl.Builder()
            .maxAge(1, TimeUnit.MINUTES)
            .build()
        response.newBuilder()
            .header("Cache-Control", cacheControl.toString())
            .build()
    }



}

