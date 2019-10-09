package com.pokumars.fitbo.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.pokumars.fitbo.ui.TAG
import java.util.*

class BootReceiver: BroadcastReceiver() {
    //This tells the app that phone has been switched on
    //This will only run after a boot so you don't need to worry about it  being enabled after onCreate always

    override fun onReceive(context: Context?, intent: Intent?) {
        //1. When phone is switched off the alarm will switch off. so this turns it back on when phone is on again.
        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {
            // Set the alarm here.
            createAlarmManager(context)
        }
    }

    fun createAlarmManager(context: Context?){
        //this is after Boot Alarm. The on start alarm is in TodayViewmodel
        Toast.makeText( context,"setting Alarm after boot", Toast.LENGTH_LONG).show()
        Log.i(TAG, "setting Alarm  after boot")

        // Set the alarm to start at approximately 00:00 p.m as specified in calendar.
        val calendar : Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
        }

        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmPendingIntent = Intent(context, StepsCheckAlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, 0, intent, 0)
        }

        //this is after Boot Alarm. The on start alarm is in TodayViewmodel
        //TODO What it should do when alarm runs is in the class StepsCheckAlarmReceiver in Utility file
        var oneDayInMilliS = 24L*60 *60 *1000
        alarmManager?.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                oneDayInMilliS,
                alarmPendingIntent
        )

        /*var twoMinutes = 2L * 60 *1000 *1000
        alarmManager?.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            twoMinutes,
            alarmPendingIntent
        )*/
    }
}

class StepsCheckAlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val currentTime = System.currentTimeMillis()

        var preferencesHelper = SharedPreferencesHelper(context!!)
        var currentSteps = preferencesHelper.getUniversalStepCount()

        preferencesHelper.setMidnighStepCount(currentSteps ?: 0f)


        //E.g
        Toast.makeText(context, "setting alarm in inStepsCheckAlarmReceiver", Toast.LENGTH_LONG).show();
        Log.i(TAG, "setting alarm in inStepsCheckAlarmReceiver ${Date().toString()}")
        Log.i(TAG, "midnight step count ------------- ${preferencesHelper.getMidnighStepCount()}")
        println("FITBO Alarm, Alarm, inReceiver println")

        // when received what should it do
        //TODO at this point, check the universal steps and set that
        // in shared preference to use as the 0 level

    }
}