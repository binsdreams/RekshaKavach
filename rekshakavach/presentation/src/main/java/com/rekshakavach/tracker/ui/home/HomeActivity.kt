package com.rekshakavach.tracker.ui.home

import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.rekshakavach.tracker.R
import com.rekshakavach.tracker.base.DaggerBaseActivity
import com.rekshakavach.tracker.di.vm.ViewModelProviderFactory
import com.rekshakavach.tracker.ui.service.Actions
import com.rekshakavach.tracker.ui.service.LocationScheduler
import com.rekshakavach.tracker.ui.service.ServiceState
import com.rekshakavach.tracker.ui.service.getServiceState
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        handleLocationPermission()
        nameText.text = "Welcome ".plus(homeViewModel.getUserName())
        actionOnService(Actions.START)
    }

    override fun  onLocationReceived(location: Location?){
        Log.i("LOCATION_LOG","onLocationReceived")
    }

    override fun displayAddressOutput(fullAddrss: String?){
        needAddress = false
        locationText.text = getString(R.string.yourLoc).plus(" -").plus(fullAddrss)
    }


    private fun actionOnService(action: Actions) {
        if (getServiceState(this) == ServiceState.STOPPED && action == Actions.STOP) return
        Intent(this, LocationScheduler::class.java).also {
            it.action = action.name
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.i("Home Activity","Starting the service in >=26 Mode")
                startForegroundService(it)
                return
            }
            Log.i("Home Activity","Starting the service in < 26 Mode")
            startService(it)
        }
    }

}
