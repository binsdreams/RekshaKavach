
package com.rekshakavach.tracker.ui.location

import android.app.IntentService
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.ResultReceiver
import android.util.Log
import com.rekshakavach.tracker.R
import java.io.IOException
import java.util.*

class FetchAddressIntentService : IntentService("FetchAddress") {

    private val TAG = "FetchAddressService"
    private var receiver: ResultReceiver? = null


    override fun onHandleIntent(intent: Intent?) {
        var errorMessage = ""

        receiver = intent?.getParcelableExtra(Constants.RECEIVER)

        if (intent == null || receiver == null) {
            Log.wtf(TAG, "No receiver received. There is nowhere to send the results.")
            return
        }

        val location = intent.getParcelableExtra<Location>(Constants.LOCATION_DATA_EXTRA)

        if (location == null) {
            errorMessage = getString(R.string.no_location_data_provided)
            Log.wtf(TAG, errorMessage)
            deliverResultToReceiver(Constants.FAILURE_RESULT, errorMessage)
            return
        }

        val geocoder = Geocoder(this, Locale.getDefault())

        var addresses: List<Address> = emptyList()

        try {
            addresses = geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                1)
        } catch (ioException: IOException) {
            errorMessage = "Service not available"
            Log.e(TAG, errorMessage, ioException)
        } catch (illegalArgumentException: IllegalArgumentException) {
            errorMessage = "invalid lat long"
            Log.e(TAG, "$errorMessage. Latitude = $location.latitude , " +
                    "Longitude = $location.longitude", illegalArgumentException)
        }
        if (addresses.isEmpty()) {
            if (errorMessage.isEmpty()) {
                errorMessage = "address not found"
                Log.e(TAG, errorMessage)
            }
            deliverResultToReceiver(Constants.FAILURE_RESULT, errorMessage)
        } else {
            val address = addresses[0]
            Log.i(TAG, "address not found")
            var builder = StringBuilder()
            builder.append(address.locality).append(",")
            builder.append(address.featureName).append(",")
            builder.append(address.subAdminArea).append(",")
            builder.append(address.postalCode).append(",")
            builder.append(address.adminArea).append(",")
            builder.append(address.countryName)
            deliverResultToReceiver(Constants.SUCCESS_RESULT, builder.toString())
        }
    }

    private fun deliverResultToReceiver(resultCode: Int, message: String) {
        val bundle = Bundle().apply { putString(Constants.RESULT_DATA_KEY, message) }
        receiver?.send(resultCode, bundle)
    }

}
