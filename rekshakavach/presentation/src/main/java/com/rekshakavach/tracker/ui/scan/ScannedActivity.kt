package com.rekshakavach.tracker.ui.scan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rekshakavach.tracker.R
import com.rekshakavach.tracker.common.showSnackBar
import com.rekshakavach.tracker.di.vm.ViewModelProviderFactory
import com.rekshakavach.tracker.domain.entity.DataEntity
import com.rekshakavach.tracker.ui.home.HomeViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_home.homeRoot
import kotlinx.android.synthetic.main.activity_scanned.*
import javax.inject.Inject

class ScannedActivity : DaggerAppCompatActivity() {

    companion object {
        private const val SCANNED_STRING: String = "scanned_string"
        fun getScannedActivity(callingClassContext: Context, encryptedString: String): Intent {
            return Intent(callingClassContext, ScannedActivity::class.java)
                    .putExtra(SCANNED_STRING, encryptedString)
        }
    }

    private lateinit var homeViewModel: HomeViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    private var userBand = "GREEN"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanned)
        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        if (intent.getSerializableExtra(SCANNED_STRING) == null)
            throw RuntimeException("No encrypted String found in intent")
        val decryptedString = intent.getStringExtra(SCANNED_STRING)
        listenForUSerInfo()
        homeViewModel.getUserProfile(decryptedString)
    }


    private fun listenForUSerInfo(){
        homeViewModel.userInfoLive.observe(this, Observer {
            when(it){
                is DataEntity.SUCCESS ->{
                    userNAme.text = it.data?.name?:""
                    userBand =  it.data?.covid_band?:"GREEN"
                    locationText.text = it.data?.address?:""
                    qrCodeImageView.setImageResource(getColorTint(it.data?.covid_band?:""))
                }
                is DataEntity.ERROR ->{
                    var error = it.error.message?:getString(R.string.sometingWentWrong)
                    homeRoot.showSnackBar(error,R.color.snack_red)
                }
            }
        })
    }

    private fun getColorTint(colorCode : String?):Int{
        var color = R.drawable.no
        when(colorCode){
            "GREEN" ->{
                color = R.drawable.yes
            }
            "RED" ->{
                color = R.drawable.no
            }
            "AMBER" ->{
                color = R.drawable.amber
            }
        }
        return color
    }

    fun onbackClicked(view :View){
        onBackPressed()
    }
}
