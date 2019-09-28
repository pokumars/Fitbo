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
        Toast.makeText( context,"setting Alarm after boot", Toast.LENGTH_LONG).show()
        Log.i(TAG, "setting Alarm  after boot")

        // Set the alarm to start at approximately 00:00 p.m as specified in calendar.
        val calendar : Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 0)
        }

        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmPendingIntent = Intent(context, StepsCheckAlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, 0, intent, 0)
        }

        //TODO What it should do when alarm runs is in the class StepsCheckAlarmReceiver in Utility file
        alarmManager?.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            alarmPendingIntent
        )
    }
}

class StepsCheckAlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val currentTime = System.currentTimeMillis()
        //E.g
        //Toast.makeText(context, "setting alarm in inStepsCheckAlarmReceiver", Toast.LENGTH_LONG).show();
        Log.i(TAG, "setting alarm in inStepsCheckAlarmReceiver ${Date().toString()}")
        println("FITBO Alarm, Alarm, inReceiver println")

        // when received what should it do
        //TODO at this point, check the universal steps and set that
        // in shared preference to use as the 0 level
    }
}