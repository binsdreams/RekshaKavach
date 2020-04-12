package com.rekshakavach.tracker.domain.entity

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable


data class UserCovidInfoEntity(
    var user_id: String? = "",
    var is_positive: Boolean? = false,
    var positive_declaration_date: String? = "",
    var is_discharged: Boolean? = null,
    var discharge_date: String? = ""
): Parcelable {

    @SuppressLint("NewApi")
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readBoolean(),
        parcel.readString(),
        parcel.readBoolean(),
        parcel.readString()
    )

    @SuppressLint("NewApi")
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(user_id)
        parcel.writeBoolean(is_positive?:false)
        parcel.writeString(positive_declaration_date)
        parcel.writeBoolean(is_positive?:false)
        parcel.writeString(discharge_date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserCovidInfoEntity> {
        override fun createFromParcel(parcel: Parcel): UserCovidInfoEntity {
            return UserCovidInfoEntity(parcel)
        }

        override fun newArray(size: Int): Array<UserCovidInfoEntity?> {
            return arrayOfNulls(size)
        }
    }
}