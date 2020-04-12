package com.rekshakavach.tracker.ui.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.*
import com.rekshakavach.tracker.R
import com.rekshakavach.tracker.RKTApplication
import com.rekshakavach.tracker.di.MainModule
import com.rekshakavach.tracker.di.NetworkModule
import com.rekshakavach.tracker.domain.repo.UserLocationRepo
import com.rekshakavach.tracker.ui.home.HomeActivity
import com.rekshakavach.tracker.ui.home.di.HomeModule
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consume

class LocationScheduler :Service(){

    private var wakeLock: PowerManager.WakeLock? = null
    private var isServiceStarted = false
    private lateinit var mLocationCallback :LocationCallback
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var userLocationRepo : UserLocationRepo ? =null
    private  var androidId :String =""

    override fun onBind(intent: Intent): IBinder? {
        Log.i("LocationScheduler","Some component want to bind with the service")
        // We don't provide binding, so return null
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
         Log.i("LocationScheduler","onStartCommand executed with startId: $startId")
        if (intent != null) {
            val action = intent.action
             Log.i("LocationScheduler","using an intent with action $action")
            when (action) {
                Actions.START.name -> startService()
                Actions.STOP.name -> stopService()
                else ->  Log.i("LocationScheduler","This should never happen. No action in the received intent")
            }
        } else {
             Log.i("LocationScheduler",
                "with a null intent. It has been probably restarted by the system."
            )
        }
        // by returning this we make sure the service is restarted if the system kills the service
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
         Log.i("LocationScheduler","The service has been created".toUpperCase())
        createNetwork()
        var notification = createNotification()
        startForeground(1, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::mLocationCallback.isInitialized) {
            fusedLocationClient?.removeLocationUpdates(mLocationCallback)
        }
         Log.i("LocationScheduler","The service has been destroyed".toUpperCase())
        Toast.makeText(this, "Service destroyed", Toast.LENGTH_SHORT).show()
    }

    private fun startService() {
        if (isServiceStarted) return
         Log.i("LocationScheduler","Starting the foreground service task")
        Toast.makeText(this, "Service starting its task", Toast.LENGTH_SHORT).show()
        isServiceStarted = true
        setServiceState(this, ServiceState.STARTED)

        // we need this lock so our service gets not affected by Doze Mode
        wakeLock =
            (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
                newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "EndlessService::lock").apply {
                    acquire()
                }
            }

        requestLocation()
    }

    private fun stopService() {
         Log.i("LocationScheduler","Stopping the foreground service")
        Toast.makeText(this, "Service stopping", Toast.LENGTH_SHORT).show()
        try {
            wakeLock?.let {
                if (it.isHeld) {
                    it.release()
                }
            }
            stopForeground(true)
            stopSelf()
        } catch (e: Exception) {
             Log.i("LocationScheduler","Service stopped without being started: ${e.message}")
        }
        isServiceStarted = false
        setServiceState(this, ServiceState.STOPPED)
    }

    private fun requestLocation(){
        var locationRequest = LocationRequest();
        locationRequest.interval = 30000;
        locationRequest.fastestInterval = 1000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationCallback =  object : LocationCallback() {
            override fun onLocationResult(var1: LocationResult?) {
                var lastLocation = var1?.lastLocation
                locationUpdate(lastLocation?.latitude?:0.0,lastLocation?.longitude?:0.0)
                Log.i("LocationUpdate","onLocationResult :"+lastLocation?.latitude)
            }
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient?.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper())
    }

    private fun createNotification(): Notification {
        val notificationChannelId = "ENDLESS SERVICE CHANNEL"

        // depending on the Android API that we're dealing with we will have
        // to use a specific method to create the notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager;
            val channel = NotificationChannel(
                notificationChannelId,
                "Endless Service notifications channel",
                NotificationManager.IMPORTANCE_HIGH
            ).let {
                it.description = "Endless Service channel"
                it.enableLights(true)
                it.lightColor = Color.RED
                it.enableVibration(true)
                it.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
                it
            }
            notificationManager.createNotificationChannel(channel)
        }

        val pendingIntent: PendingIntent = Intent(this, HomeActivity::class.java).let { notificationIntent ->
            PendingIntent.getActivity(this, 0, notificationIntent, 0)
        }

        val builder: Notification.Builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) Notification.Builder(
            this,
            notificationChannelId
        ) else Notification.Builder(this)

        return builder
            .setContentTitle("Covid Tracking")
            .setContentText("We are tracking you to save form covid")
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.mipmap.app_icon_round)
            .setTicker("Ticker text")
            .setPriority(Notification.PRIORITY_HIGH) // for under android 26 compatibility
            .build()
    }

    private fun createNetwork(){
        var network = NetworkModule()
        var cache = MainModule(application = application as RKTApplication).provideCacheManager()
        var retrofit =network.provideRetrofitInstance(network.provideOkHttpClient(cache))
        var homeModule = HomeModule()
        var api = homeModule.provideUserUserLocationApi(retrofit)
        androidId = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
        userLocationRepo = homeModule.provideUserLocationRepo(api,cache)
    }

    fun locationUpdate(lat:Double,lng :Double) {
        GlobalScope.launch {
            userLocationRepo?.locationUpdate(CoroutineScope(coroutineContext),lat,lng,androidId)?.consume {
                var response = this.receive()
                withContext(Dispatchers.Main) {
                    Log.i("LocationUpdate","send to server $response")
                }
            }
        }
    }
}
