package com.rekshakavach.tracker.ui.home

import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.rekshakavach.tracker.R
import com.rekshakavach.tracker.base.BaseActivity

class HomeActivty : BaseActivity() {

    companion object{
        fun getIntent(context : Context) : Intent {
            var intent =  Intent(context, HomeActivty::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            return intent
        }
    }

    private var locationLive = MutableLiveData<Location>()
    private var addressLive = MutableLiveData<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    fun requestForLocationAndHandlePermission(locLive : MutableLiveData<Location>, adLive: MutableLiveData<String>){
        addressLive = adLive
        locationLive = locLive
        handleLocationPermission()
    }

    override fun  onLocationReceived(location: Location?){
        locationLive.postValue(location)
    }

    override fun displayAddressOutput(fullAddrss: String?){
        addressLive.postValue(fullAddrss)
    }


}
