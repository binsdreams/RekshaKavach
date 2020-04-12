package com.rekshakavach.tracker.ui.scan

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.rekshakavach.tracker.R
import com.rekshakavach.tracker.common.showSnackBar
import com.rekshakavach.tracker.di.vm.ViewModelProviderFactory
import com.rekshakavach.tracker.domain.entity.DataEntity
import com.rekshakavach.tracker.ui.home.HomeViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_home.homeRoot
import kotlinx.android.synthetic.main.activity_home.qrCodeImageView
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
                    bmp.setPixel(x, y, if (bitMatrix[x, y]) getColorTint(userBand) else Color.WHITE)
                }
            }
            qrCodeImageView.setImageBitmap(bmp)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }

    private fun listenForUSerInfo(){
        homeViewModel.userInfoLive.observe(this, Observer {
            when(it){
                is DataEntity.SUCCESS ->{
                    userNAme.text = it.data?.name?:""
                    userBand =  it.data?.covid_band?:"GREEN"
                    setImageBitmap( it.data?.user_id?:"9999999")
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

    fun onbackClicked(view :View){
        onBackPressed()
    }
}
