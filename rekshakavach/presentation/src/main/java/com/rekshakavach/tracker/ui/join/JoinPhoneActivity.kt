package com.rekshakavach.tracker.ui.join

import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import com.rekshakavach.tracker.R
import com.rekshakavach.tracker.base.BaseActivity

class JoinPhoneActivity : BaseActivity() {

    companion object{
        fun getIntent(context : Context) : Intent {
            var intent =  Intent(context, JoinPhoneActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_phone)
    }

    fun requestForLocationAndHandlePermission(){
        handleLocationPermission()
    }

    override fun  onLocationReceived(location: Location?){

    }

}
