package com.rekshakavach.tracker.ui.home

import androidx.lifecycle.MutableLiveData
import com.rekshakavach.tracker.base.BaseViewModel
import com.rekshakavach.tracker.domain.entity.DataEntity
import com.rekshakavach.tracker.domain.entity.UserInfoEntity
import com.rekshakavach.tracker.domain.repo.UserLocationRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val userRepos :UserLocationRepo): BaseViewModel() {

    var locationUpdateLive = MutableLiveData<DataEntity<UserInfoEntity>>()

    fun locationUpdate(lat:Double,lng :Double,deviceId :String) {
        launch {
            userRepos.locationUpdate(CoroutineScope(coroutineContext),lat,lng,deviceId).consume {
                var response = this.receive()
                withContext(Dispatchers.Main) {
                    locationUpdateLive.postValue(response)
                }
            }
        }
    }

    fun getUserName():String?{
        return userRepos.getUser()?.name
    }

}