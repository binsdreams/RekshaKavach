package com.rekshakavach.tracker.domain.entity

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

data class UserInfoEntity(
    var user_id: String? = "",
    var phone: String? = "",
    var name: String? = "",
    var dob: String? = null,
    var registered_date: String? = "",
    var sex: String? = "",
    var address: String? = "",
    var covid_band: String? = ""
): Parcelable {

    @SuppressLint("NewApi")
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    @SuppressLint("NewApi")
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(user_id)
        parcel.writeString(name)
        parcel.writeString(phone)
        parcel.writeString(dob)
        parcel.writeString(registered_date)
        parcel.writeString(sex)
        parcel.writeString(covid_band)
        parcel.writeString(address)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserInfoEntity> {
        override fun createFromParcel(parcel: Parcel): UserInfoEntity {
            return UserInfoEntity(parcel)
        }

        override fun newArray(size: Int): Array<UserInfoEntity?> {
            return arrayOfNulls(size)
        }
    }
}