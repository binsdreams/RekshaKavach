package com.rekshakavach.tracker.ui.home

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.rekshakavach.tracker.R
import com.rekshakavach.tracker.base.DaggerBaseActivity
import com.rekshakavach.tracker.common.showSnackBar
import com.rekshakavach.tracker.di.vm.ViewModelProviderFactory
import com.rekshakavach.tracker.domain.entity.DataEntity
import com.rekshakavach.tracker.domain.entity.UserInfoEntity
import com.rekshakavach.tracker.ui.mark.MarkCovidActivity
import com.rekshakavach.tracker.ui.scan.ScanQrCodeActivity
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
            return intent
        }
    }

    private lateinit var homeViewModel: HomeViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    private var userInfoEntity :UserInfoEntity?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        userInfoEntity = homeViewModel.getUser()
        handleLocationPermission()
        actionOnService(Actions.START)
        homeViewModel.getMyProfile()
        listenForUSerInfo()
        setClick()
        nameText.text = "Welcome ".plus(homeViewModel.getUserName())
        setImageBitmap(userInfoEntity?.hash_code?:"1586682688427")
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

    private fun setClick(){
        markMyself.setOnClickListener{
            startActivity(MarkCovidActivity.getIntent(this))
        }
    }

    private fun setImageBitmap(content: String) {
        if(content.isNullOrEmpty()){
            return
        }
        val writer = QRCodeWriter()
        try {
            val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bmp.setPixel(x, y, if (bitMatrix[x, y]) getColorTint(userInfoEntity?.covid_band) else Color.WHITE)
                }
            }
            qrCodeImageView.setImageBitmap(bmp)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }

    private fun listenForUSerInfo(){
        homeViewModel.myProfileLive.observe(this, Observer {
            getProfileCall()
            when(it){
                is DataEntity.SUCCESS ->{
                    userInfoEntity = it.data
                    if(userInfoEntity?.hash_code.isNullOrEmpty().not()){
                        setImageBitmap(userInfoEntity?.hash_code!!)
                    }
                }
                is DataEntity.ERROR ->{
                    var error = it.error.message?:getString(R.string.sometingWentWrong)
                    homeRoot.showSnackBar(error,R.color.snack_red)
                }
            }
        })
    }

    private fun getColorTint(colorCode : String?):Int{
        var color =Color.BLACK
       when(colorCode){
           "GREEN" ->{
               color = ContextCompat.getColor(this,R.color.snack_green)
           }
           "RED" ->{
               color=  ContextCompat.getColor(this,R.color.snack_red)
           }
           "AMBER" ->{
               color = ContextCompat.getColor(this,R.color.amber)
           }
       }
        return color
    }

    private fun getProfileCall(){
        markMyself?.postDelayed({
            Log.i("BINIL","getProfileCall")
            homeViewModel?.getMyProfile()
        },30*1000)

    }

    fun onQrCodeClick(view :View){
        startActivity(ScanQrCodeActivity.getScanQrCodeActivity(this))
    }

}
