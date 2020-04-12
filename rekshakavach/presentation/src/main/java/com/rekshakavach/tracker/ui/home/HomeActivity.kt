package com.rekshakavach.tracker.ui.home

import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.rekshakavach.tracker.R
import com.rekshakavach.tracker.base.DaggerBaseActivity
import com.rekshakavach.tracker.data.common.parseDateToServerFormat
import com.rekshakavach.tracker.di.vm.ViewModelProviderFactory
import com.rekshakavach.tracker.domain.entity.UserCovidInfoEntity
import com.rekshakavach.tracker.ui.picker.DatePickerFragment
import com.rekshakavach.tracker.ui.service.Actions
import com.rekshakavach.tracker.ui.service.LocationScheduler
import com.rekshakavach.tracker.ui.service.ServiceState
import com.rekshakavach.tracker.ui.service.getServiceState
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*
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
    private var isDischargeSelected = false;
    private var isTestedPositive = false;
    private var isDischarged = false;
    private  var androidId :String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        androidId = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        homeViewModel.initToday()
        handleLocationPermission()
        nameText.text = "Welcome ".plus(homeViewModel.getUserName())
        actionOnService(Actions.START)
        initClicks()
        listenForUpdate()
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

    private fun initClicks(){
        testSwitch.setOnCheckedChangeListener { compoundButton, b ->
            isTestedPositive = b
        }

        isDiscahrgedSwitch.setOnCheckedChangeListener { compoundButton, b ->
            isDischarged = b
        }
        positive_declaration_date.setOnClickListener{
            isDischargeSelected = false
            showDatePicker(homeViewModel.getTestDate().time)
        }

        discharge_date.setOnClickListener{
            isDischargeSelected = true
            showDatePicker(homeViewModel.getDischargeDate().time)
        }

        covidBtn.setOnClickListener{
            homeViewModel.updateCovidAsync(
                UserCovidInfoEntity(
                    user_id = androidId,
                    discharge_date = parseDateToServerFormat(homeViewModel.getDischargeDate()),
                    is_discharged = isDischarged,
                    is_positive = isTestedPositive,
                    positive_declaration_date = parseDateToServerFormat(homeViewModel.getTestDate())
                )
            )
        }
    }

    private fun showDatePicker(milliseconds :Long){
        val c = Calendar.getInstance()
        c.timeInMillis = milliseconds
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        DatePickerFragment().display(
            manager = supportFragmentManager,
            tag = "DIALOG_TAG",
            calendar = Calendar.getInstance().apply { set(year, month, day) },
            callback = object : DatePickerFragment.Callback {
                override fun onDateSelected(calendar: Calendar) {
                    if(isDischargeSelected){
                        homeViewModel.setDischargeDate(calendar.time)
                        discharge_date.text = homeViewModel.getDischargeDateStr()
                    }else{
                        homeViewModel.setDignoseDate(calendar.time)
                        positive_declaration_date.text = homeViewModel.getTestDateStr()
                    }

                }
            })
    }


    private fun listenForUpdate(){
        homeViewModel.covidUpdateLive.observe(this, androidx.lifecycle.Observer {
            dismissProgress()
        })
    }

    private fun dismissProgress(){
        covidProgress.visibility = View.GONE
        covidBtn.text = getString(R.string.save)
    }
}
