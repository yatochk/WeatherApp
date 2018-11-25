package com.yatochk.weather.model.location

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.support.v4.app.ActivityCompat


class LocationTask(activity: Activity) {
    private var locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private var listener: ((String) -> Unit)? = null
    fun setLocationListener(listener: (String) -> Unit) {
        this.listener = listener
    }

    init {
        val criteria = Criteria()
        criteria.accuracy = Criteria.ACCURACY_COARSE
        criteria.powerRequirement = Criteria.POWER_LOW
        criteria.isAltitudeRequired = false
        criteria.isBearingRequired = false
        criteria.isCostAllowed = true
        val provider = locationManager.getBestProvider(criteria, true)
        val locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                val address = Geocoder(activity)
                    .getFromLocation(location.latitude, location.longitude, 1)[0]
                listener?.invoke(address.locality)
                locationManager.removeUpdates(this)
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            }

            override fun onProviderEnabled(provider: String?) {
            }

            override fun onProviderDisabled(provider: String?) {
            }
        }

        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        )
        // Why run??
            run {
                ActivityCompat.requestPermissions(
                    activity, arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ), 1
                )
            } else {
            locationManager.requestLocationUpdates(
                provider,
                500,
                100f,
                locationListener
            )
        }
    }
}