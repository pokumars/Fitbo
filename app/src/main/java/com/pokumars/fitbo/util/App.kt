package com.pokumars.fitbo.util

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

val STEPS_CHANNEL_ID ="stepsServiceChannel"
val TAG = "FITBOAPP"
class App: Application() {
    override fun onCreate() {
        super.onCreate()

        createStepsNotificationChannel()
    }

    fun createStepsNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            var serviceChannel = NotificationChannel(
                STEPS_CHANNEL_ID,
                "Steps Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            var manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

}