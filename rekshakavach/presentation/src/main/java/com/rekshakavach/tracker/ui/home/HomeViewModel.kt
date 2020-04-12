package com.rekshakavach.tracker.ui.home

import androidx.lifecycle.MutableLiveData
import com.rekshakavach.tracker.base.BaseViewModel
import com.rekshakavach.tracker.domain.entity.DataEntity
import com.rekshakavach.tracker.domain.entity.UserInfoEntity
import com.rekshakavach.tracker.domain.repo.ProfileRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val userRepos :ProfileRepo): BaseViewModel() {

    var myProfileLive = MutableLiveData<DataEntity<UserInfoEntity>>()
    var userInfoLive = MutableLiveData<DataEntity<UserInfoEntity>>()

    fun getUserProfile(userId :String) {
        launch {
            userRepos.getUserProfileAsync(CoroutineScope(coroutineContext),userId).consume {
                var response = this.receive()
                withContext(Dispatchers.Main) {
                    userInfoLive.postValue(response)
                }
            }
        }
    }

    fun getMyProfile() {
        launch {
            userRepos.getMyProfile(CoroutineScope(coroutineContext)).consume {
                var response = this.receive()
                withContext(Dispatchers.Main) {
                    myProfileLive.postValue(response)
                }
            }
        }
    }

    fun getUserName():String?{
        return userRepos.getUser()?.name
    }

    fun getUser():UserInfoEntity{
        return userRepos.getUser()
    }

}