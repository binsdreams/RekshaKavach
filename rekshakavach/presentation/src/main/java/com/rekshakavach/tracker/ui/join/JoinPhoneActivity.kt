package com.rekshakavach.tracker.ui.join

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rekshakavach.tracker.R

class JoinPhoneActivity : AppCompatActivity() {

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

}
