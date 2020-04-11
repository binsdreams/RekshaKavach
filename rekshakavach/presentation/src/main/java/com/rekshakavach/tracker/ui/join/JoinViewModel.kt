package com.rekshakavach.tracker.ui.join

import androidx.lifecycle.MutableLiveData
import com.rekshakavach.tracker.base.BaseViewModel
import com.rekshakavach.tracker.domain.entity.BaseEntity
import com.rekshakavach.tracker.domain.entity.DataEntity
import com.rekshakavach.tracker.domain.entity.LoginEntity
import com.rekshakavach.tracker.domain.repo.PhoneLoginRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class JoinViewModel @Inject constructor(private val generateOtpRepo :PhoneLoginRepo): BaseViewModel() {

    var otpResponseLive = MutableLiveData<DataEntity<BaseEntity>>()
    var otpValidateLive = MutableLiveData<DataEntity<LoginEntity>>()

    fun generateOtp(code :String,phone :String) {
        launch {
            generateOtpRepo.generateOtp(CoroutineScope(coroutineContext),code,phone).consume {
                var response = this.receive()
                withContext(Dispatchers.Main) {
                    otpResponseLive.postValue(response)
                }
            }
        }
    }

    fun validateOtp(code :String,phone :String,otp :String) {
        launch {
            generateOtpRepo.validateOtp(CoroutineScope(coroutineContext),code,phone,otp).consume {
                var response = this.receive()
                withContext(Dispatchers.Main) {
                    otpValidateLive.postValue(response)
                }
            }
        }
    }

    fun resendOtp(code :String,phone :String) {
        launch {
            generateOtpRepo.resendOtp(CoroutineScope(coroutineContext),code,phone).consume {
                var response = this.receive()
                withContext(Dispatchers.Main) {
                    otpResponseLive.postValue(response)
                }
            }
        }
    }

    fun saveUser(username :String,email :String){
        launch {
            generateOtpRepo.saveUserProfile(CoroutineScope(coroutineContext),email,username ).consume {
                var response = this.receive()
                withContext(Dispatchers.Main) {
                    otpResponseLive.postValue(response)
                }
            }
        }
    }

    fun isUserLoggedIn():Boolean{
        return generateOtpRepo.getToken().isNotEmpty()
    }
}