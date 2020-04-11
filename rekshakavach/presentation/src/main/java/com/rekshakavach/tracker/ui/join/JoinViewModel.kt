package com.rekshakavach.tracker.ui.join

import androidx.lifecycle.MutableLiveData
import com.rekshakavach.tracker.base.BaseViewModel
import com.rekshakavach.tracker.domain.entity.DataEntity
import com.rekshakavach.tracker.domain.entity.UserInfoEntity
import com.rekshakavach.tracker.domain.repo.UserRegistrationRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class JoinViewModel @Inject constructor(private val userRepos :UserRegistrationRepo): BaseViewModel() {

    private val DATE_FORMAT_PATTERN = "dd-MM-yyyy"
    private lateinit var dobSelected :Date
    private var dobStr = ""
    private  var dateFormat = SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.US)
    var userResponseLive = MutableLiveData<DataEntity<UserInfoEntity>>()

    fun register(userInfoEntity: UserInfoEntity) {
        launch {
            userRepos.register(CoroutineScope(coroutineContext),userInfoEntity).consume {
                var response = this.receive()
                withContext(Dispatchers.Main) {
                    userResponseLive.postValue(response)
                }
            }
        }
    }


    fun initToday(){
        dobSelected = Date()
        dobStr =dateFormat.format(dobSelected.time)
    }

    fun setDob(date :Date){
        dobSelected = date
        dobStr =dateFormat.format(date.time)
    }

    fun getDob():Date{
        return dobSelected
    }

    fun getDobStr():String{
        return dobStr
    }

    fun isUserLoggedIn():Boolean{
        return userRepos.getToken().isNotEmpty()
    }

}