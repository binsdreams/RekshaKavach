package com.rekshakavach.tracker.ui.mark

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.rekshakavach.tracker.R
import com.rekshakavach.tracker.base.DaggerBaseActivity
import com.rekshakavach.tracker.common.showSnackBar
import com.rekshakavach.tracker.data.common.parseDateToServerFormat
import com.rekshakavach.tracker.di.vm.ViewModelProviderFactory
import com.rekshakavach.tracker.domain.entity.UserCovidInfoEntity
import com.rekshakavach.tracker.ui.picker.DatePickerFragment
import com.rekshakavach.tracker.ui.service.Actions
import com.rekshakavach.tracker.ui.service.LocationScheduler
import com.rekshakavach.tracker.ui.service.ServiceState
import com.rekshakavach.tracker.ui.service.getServiceState
import kotlinx.android.synthetic.main.activity_markcovid.*
import java.util.*
import javax.inject.Inject

class MarkCovidActivity : DaggerBaseActivity() {

    companion object{
        fun getIntent(context : Context) : Intent {
            var intent =  Intent(context, MarkCovidActivity::class.java)
            return intent
        }
    }

    private lateinit var homeViewModel: MArkCovidViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    private var isDischargeSelected = false;
    private var isTestedPositive = false;
    private var isDischarged = false;
    private  var androidId :String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_markcovid)
        androidId = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
        homeViewModel = ViewModelProvider(this, viewModelFactory).get(MArkCovidViewModel::class.java)
        homeViewModel.initToday()
        actionOnService(Actions.START)
        initClicks()
        listenForUpdate()
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
        action_backBtn.setOnClickListener{
            onBackPressed()
        }
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
            covidProgress.visibility = View.VISIBLE
            covidBtn.text = ""
            homeViewModel.updateCovidAsync(
                UserCovidInfoEntity(
                    user_id = homeViewModel.getUserId(),
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
            rootCovid.showSnackBar("Saved",R.color.snack_green)
            rootCovid.postDelayed({
                this.finish()
            },500)
        })
    }

    private fun dismissProgress(){
        covidProgress.visibility = View.GONE
        covidBtn.text = getString(R.string.save)
    }
}
