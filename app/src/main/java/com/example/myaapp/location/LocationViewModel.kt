package com.example.myaapp.location

import android.Manifest
import android.content.Context
import android.location.Geocoder
import android.os.Looper
import androidx.annotation.RequiresPermission
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myaapp.location.LocationActivity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.util.Locale

class LocationViewModel : ViewModel() {

    val locationText = MutableLiveData<String>()

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    fun getLocation(context: Context) {
        val fusedLocationProviders = LocationServices.getFusedLocationProviderClient(context)
        val request = LocationRequest.Builder(
            LocationRequest.PRIORITY_HIGH_ACCURACY,
            5000
        ).build()

        val callback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location = locationResult.lastLocation
                if (location != null) {
                    val geocoder = Geocoder(context, Locale.getDefault())
                    val locationDetails = geocoder.getFromLocation(
                        location.latitude,
                        location.longitude,
                        1
                    )
                    val output =
                        "Locality : " + locationDetails?.get(0)?.locality + "\n,Address " +
                                locationDetails?.get(0)?.getAddressLine(0)

                    locationText.value = output
                }
                super.onLocationResult(locationResult)
            }
        }
        fusedLocationProviders.requestLocationUpdates(
            request,
            callback,
            Looper.getMainLooper()
        )
    }
}