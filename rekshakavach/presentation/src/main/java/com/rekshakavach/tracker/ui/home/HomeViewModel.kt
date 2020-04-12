package com.rekshakavach.tracker.ui.home

import androidx.lifecycle.MutableLiveData
import com.rekshakavach.tracker.base.BaseViewModel
import com.rekshakavach.tracker.domain.entity.BaseEntity
import com.rekshakavach.tracker.domain.entity.DataEntity
import com.rekshakavach.tracker.domain.entity.UserCovidInfoEntity
import com.rekshakavach.tracker.domain.repo.UserCovidInfoRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val userRepos :UserCovidInfoRepo): BaseViewModel() {

    var covidUpdateLive = MutableLiveData<DataEntity<BaseEntity>>()
    private val DATE_FORMAT_PATTERN = "dd-MM-yyyy"
    private lateinit var dischargeSelected : Date
    private var dischargeDateStr = ""
    private lateinit var diagnoseDateSelected : Date
    private var diagnoseDateStr = ""
    private  var dateFormat = SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.US)

    fun updateCovidAsync(user :UserCovidInfoEntity) {
        launch {
            userRepos.updateCovidAsync(CoroutineScope(coroutineContext),user).consume {
                var response = this.receive()
                withContext(Dispatchers.Main) {
                    covidUpdateLive.postValue(response)
                }
            }
        }
    }

    fun getUserName():String?{
        return userRepos.getUser()?.name
    }

    fun initToday(){
        dischargeSelected = Date()
        diagnoseDateSelected = Date()
        dischargeDateStr =dateFormat.format(dischargeSelected.time)
        diagnoseDateStr=dateFormat.format(diagnoseDateSelected.time)
    }

    fun setDignoseDate(date :Date){
        diagnoseDateSelected = date
        diagnoseDateStr =dateFormat.format(date.time)
    }

    fun setDischargeDate(date :Date){
        dischargeSelected = date
        diagnoseDateStr =dateFormat.format(date.time)
    }

    fun getTestDate():Date{
        return diagnoseDateSelected
    }

    fun getTestDateStr():String{
        return diagnoseDateStr
    }

    fun getDischargeDate():Date{
        return dischargeSelected
    }

    fun getDischargeDateStr():String{
        return dischargeDateStr
    }

}