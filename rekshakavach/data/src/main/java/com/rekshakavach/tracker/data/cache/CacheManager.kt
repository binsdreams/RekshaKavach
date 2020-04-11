package com.rekshakavach.tracker.data.cache

import android.content.SharedPreferences
import com.google.gson.Gson
import com.rekshakavach.tracker.domain.entity.ProfileEntity

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

    fun saveProfile(profileEntity :ProfileEntity){
        var userGson = Gson().toJson(profileEntity)
        with ( sharedPreferences.edit()) {
            putString(AUTHORIY_PROFILE, userGson)
            commit()
        }
    }

    fun getProfile():ProfileEntity{
        var jsonString = sharedPreferences.getString(AUTHORIY_PROFILE,"")
        return Gson().fromJson(jsonString, ProfileEntity::class.java)
    }
}