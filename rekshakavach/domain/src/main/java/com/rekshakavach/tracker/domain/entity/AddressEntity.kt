package com.rekshakavach.tracker.domain.entity

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

data class AddressEntity(
    var id: Int? = 0,
    var profile_id: Int? = 0,
    var address_type: String? = "",
    var default: Int? = 0,
    var display_name: String? = "",
    var mobile: String? = "",
    var building_name: String? = "",
    var street_name: String? = "",
    var landmark: String? = "",
    var city_id: String? = "",
    var city_name: String? = "",
    var pin_code: String? = "",
    var country_id: Int? = 0,
    var lat: Double? = 0.0,
    var long: Double? = 0.0
): Parcelable {

    @SuppressLint("NewApi")
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readDouble()
    )

    @SuppressLint("NewApi")
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id?:0)
        parcel.writeInt(profile_id?:0)
        parcel.writeString(address_type)
        parcel.writeInt(default?:0)
        parcel.writeString(display_name)
        parcel.writeString(mobile)
        parcel.writeString(building_name)
        parcel.writeString(street_name)
        parcel.writeString(landmark)
        parcel.writeString(city_id)
        parcel.writeString(city_name)
        parcel.writeString(pin_code)
        parcel.writeInt(country_id?:0)
        parcel.writeDouble(lat?:0.0)
        parcel.writeDouble(long?:0.0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AddressEntity> {
        override fun createFromParcel(parcel: Parcel): AddressEntity {
            return AddressEntity(parcel)
        }

        override fun newArray(size: Int): Array<AddressEntity?> {
            return arrayOfNulls(size)
        }
    }
}