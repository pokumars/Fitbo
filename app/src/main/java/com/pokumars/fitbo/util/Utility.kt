package com.pokumars.fitbo.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.pokumars.fitbo.ui.TAG
import java.util.*

/*class BootReceiver: BroadcastReceiver() {
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
        /*val calendar : Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
        }*/
        val calendar = Calendar.getInstance()
        calendar.setTimeInMillis(System.currentTimeMillis())
        calendar.set(Calendar.HOUR_OF_DAY, 2)
        calendar.set(Calendar.MINUTE, 30)

        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmPendingIntent = Intent(context, StepsCheckAlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, 0, intent, 0)
        }

        //this is after Boot Alarm. The on start alarm is in TodayViewmodel
        val twoMinutes =(2L*60* 1000)
        val tenMinutes =(10L*60* 1000)
        val anHour = (60L * 60 *1000)
        val oneDayInMillis =24* anHour

        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
            twoMinutes,
                alarmPendingIntent
        )

    }
}*/
class DeviceBootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        //1. When phone is switched off the alarm will switch off. so this turns it back on when phone is on again.
        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {
            // Set the alarm here.
            createAlarmManager(context)

            /*val serviceIntent = Intent(context, StepsForegroundService::class.java)
            ContextCompat.startForegroundService(context!!, serviceIntent)*/


            val service = Intent(context, StepsForegroundService::class.java)
            //context.startService(service)
            context?.startForegroundService(service)
        }
    }

    fun createAlarmManager(context: Context?) {
        //this is after Boot Alarm. The on start alarm is in TodayViewmodel
        Toast.makeText(context, "setting Alarm after boot", Toast.LENGTH_LONG).show()
        Log.i(TAG, "setting Alarm  after boot")

        // Set the alarm to start at approximately 00:00 p.m as specified in calendar.
        val calendar = Calendar.getInstance()
        calendar.setTimeInMillis(System.currentTimeMillis())
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 55)


        /* Setting the alarm here */
        val alarmIntent = Intent(context, StepsCheckAlarmReceiver::class.java)
        val alarmPendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0)

        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        //this is after Boot Alarm. The on start alarm is in TodayViewmodel
        //val twoMinutes = (2L * 60 * 1000)
        //val tenMinutes = (10L * 60 * 1000)
        val anHour = (60L * 60 * 1000)
        val oneDayInMillis = 24 * anHour

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            oneDayInMillis,
            alarmPendingIntent
        )
    }
}


class StepsCheckAlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val preferencesHelper = SharedPreferencesHelper(context!!)
        val currentSteps = preferencesHelper.getUniversalStepCount()

        preferencesHelper.setMidnighStepCount(currentSteps ?: 0f)

        //Toast.makeText(context, "setting alarm in inStepsCheckAlarmReceiver", Toast.LENGTH_LONG).show()
        Log.i(TAG, "setting alarm in inStepsCheckAlarmReceiver ${Date().toString()}")
        Log.i(TAG, "new midnight step count after selfSetting ------------- ${preferencesHelper.getMidnighStepCount()} -------${Date().toString()}")
    }
}