package com.rekshakavach.tracker.ui.home

import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.provider.Settings.Secure
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.rekshakavach.tracker.R
import com.rekshakavach.tracker.base.DaggerBaseActivity
import com.rekshakavach.tracker.di.vm.ViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject


class HomeActivity : DaggerBaseActivity() {

    companion object{
        fun getIntent(context : Context) : Intent {
            var intent =  Intent(context, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            return intent
        }
    }

    private lateinit var homeViewModel: HomeViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    private  var androidId :String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        androidId = Secure.getString(this.contentResolver, Secure.ANDROID_ID)
        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        handleLocationPermission()
        nameText.text = "Welcome ".plus(homeViewModel.getUserName())
    }

    override fun  onLocationReceived(location: Location?){
        Log.i("LOCATION_LOG","onLocationReceived")
        homeViewModel.locationUpdate(location?.longitude?:0.0,location?.longitude?:0.0,androidId)
    }

    override fun displayAddressOutput(fullAddrss: String?){
        needAddress = false
        locationText.text = getString(R.string.yourLoc).plus(" -").plus(fullAddrss)
    }


}
