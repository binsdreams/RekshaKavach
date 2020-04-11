package com.rekshakavach.tracker.data.cache

import android.content.SharedPreferences
import com.google.gson.Gson
import com.rekshakavach.tracker.domain.entity.UserInfoEntity

class CacheManager(private val sharedPreferences: SharedPreferences) {

    fun saveAccess(access :String){
        with ( sharedPreferences.edit()) {
            putString(AUTHORIY_ACCESS, access)
            commit()
        }
    }

    fun getAccessKey():String{
        return sharedPreferences.getString(AUTHORIY_ACCESS,"")?:""
   }

    fun clear(){
        with (sharedPreferences.edit()) {
            clear()
            commit()
        }
    }

    fun saveProfile(userInfoEntity : UserInfoEntity){
        var userGson = Gson().toJson(userInfoEntity)
        with ( sharedPreferences.edit()) {
            putString(AUTHORIY_PROFILE, userGson)
            commit()
        }
    }

    fun getProfile():UserInfoEntity{
        var jsonString = sharedPreferences.getString(AUTHORIY_PROFILE,"")
        return Gson().fromJson(jsonString, UserInfoEntity::class.java)
    }
}