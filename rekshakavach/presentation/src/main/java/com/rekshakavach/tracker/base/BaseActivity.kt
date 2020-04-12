package com.rekshakavach.tracker.base

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnSuccessListener
import com.rekshakavach.tracker.R
import com.rekshakavach.tracker.common.LocationUtils
import com.rekshakavach.tracker.common.showSnackBar
import com.rekshakavach.tracker.ui.join.permissions.LocationEnableFragment
import com.rekshakavach.tracker.ui.join.permissions.LocationPermissionFragment
import com.rekshakavach.tracker.ui.location.Constants
import com.rekshakavach.tracker.ui.location.FetchAddressIntentService
import kotlinx.android.synthetic.main.layout_action.*


open class BaseActivity : AppCompatActivity() {
    private lateinit var mLocationCallback :LocationCallback
    private var lastLocation: Location? = null
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private lateinit var resultReceiver: AddressResultReceiver
    private var addressOutput :String?= null
    var needAddress = true
    fun initAction(backBtn: Boolean, titleResId: Int, cartBtn: Boolean) {
        if (titleResId != 0) {
            action_title?.setText(titleResId)
        }

        action_backBtn?.setOnClickListener {
            onBackPressed();
        }
        action_backBtn?.visibility = if (backBtn) View.VISIBLE else View.GONE
    }

    fun handleLocationPermission(){
        resultReceiver = AddressResultReceiver(Handler())
        if(LocationUtils.isLocationPermissionDeniedForever(this)){
            val fragment = LocationPermissionFragment.getInstance()
            fragment.setPermissionCallback{
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = Uri.fromParts("package", this.packageName, null)
                    startActivity(intent)
            }
            fragment.show(supportFragmentManager, "AddAddressFragment")
        }else  if(LocationUtils.isLocationPermissionsGranted(this).not()){
            val fragment = LocationPermissionFragment.getInstance()
            fragment.setPermissionCallback {
                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                    requestPermissions(arrayOf( Manifest.permission.ACCESS_FINE_LOCATION), LocationUtils.REQUEST_ACCESS_FINE_LOCATION)
                    LocationUtils.markLocationPermissionRequested(this)
                }
            }
            fragment.show(supportFragmentManager, "AddAddressFragment")
        } else if(LocationUtils.isLocationEnabled(this).not()){
            val fragment = LocationEnableFragment.getInstance()
            fragment.show(supportFragmentManager, "AddAddressFragment")
        }else{
            requestLocation()
        }
    }

    private fun requestLocation(){
        var locationRequest = LocationRequest();
        locationRequest.interval = 30000;
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_LOW_POWER
        mLocationCallback =  object : LocationCallback() {
            override fun onLocationResult(var1: LocationResult?) {
                getLastKnownLocation()
                lastLocation = var1?.lastLocation
                onLocationReceived(location = var1?.lastLocation)
                fusedLocationClient?.removeLocationUpdates(this)
            }
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient?.requestLocationUpdates(locationRequest, mLocationCallback, null)
    }

    private fun getLastKnownLocation() {
        fusedLocationClient?.lastLocation?.addOnSuccessListener(this, OnSuccessListener { location ->
            if (location == null) {
                return@OnSuccessListener
            }

            lastLocation = location
            onLocationReceived(location = lastLocation)
            if (!Geocoder.isPresent()) {
                window.decorView.showSnackBar(getString(R.string.no_geocoder_available),R.color.snack_red)
                return@OnSuccessListener
            }
           if(needAddress) startIntentService()
        })?.addOnFailureListener(this) { e -> Log.w("GEO", "getLastLocation:onFailure", e) }
    }

    private fun startIntentService() {
        val intent = Intent(this, FetchAddressIntentService::class.java).apply {
            putExtra(Constants.RECEIVER, resultReceiver)
            putExtra(Constants.LOCATION_DATA_EXTRA, lastLocation)
        }
        startService(intent)
    }

     open fun onLocationReceived(location: Location?){

    }

    open fun displayAddressOutput(fullAddrss: String?){

    }

    override fun onDestroy() {
        if (::mLocationCallback.isInitialized) {
            fusedLocationClient?.removeLocationUpdates(mLocationCallback)
        }
        super.onDestroy()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LocationUtils.REQUEST_ACCESS_FINE_LOCATION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    handleLocationPermission()
                }
            }
        }
    }

    private inner class AddressResultReceiver internal constructor(handler: Handler) : ResultReceiver(handler) {
        override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
            addressOutput = resultData.getString(Constants.RESULT_DATA_KEY)?:""
            if (resultCode == Constants.SUCCESS_RESULT) {
                displayAddressOutput(addressOutput)
            }
        }
    }
}
