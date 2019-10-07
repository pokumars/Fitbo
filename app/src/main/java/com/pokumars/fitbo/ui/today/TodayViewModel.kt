package com.pokumars.fitbo.ui.today

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pokumars.fitbo.*
import com.pokumars.fitbo.ui.TAG
import com.pokumars.fitbo.util.BootReceiver
import com.pokumars.fitbo.util.SharedPreferencesHelper
import com.pokumars.fitbo.util.StepsCheckAlarmReceiver
import java.util.*

class TodayViewModel(application: Application) : BaseViewModel(application) {

    private val context = getApplication<Application>().applicationContext


    private var preferencesHelper =
        SharedPreferencesHelper(getApplication())

    var alarmManager: AlarmManager? = null
    private lateinit var alarmPendingIntent: PendingIntent
    private var appUsedBefore: Boolean = false
    fun todayStepCount():Float {
        return preferencesHelper.getUniversalStepCount()?.minus(preferencesHelper.getMidnighStepCount()!!)!!
    }


    private val _text = MutableLiveData<String>().apply {
        value = (preferencesHelper.getUniversalStepCount()?.minus( preferencesHelper.getMidnighStepCount()!!)).toString()
    }
    val text: LiveData<String> = _text

    fun setBootReceiverEnabled() {
        appUsedBefore = preferencesHelper.getAppFirstUse()!!

        //If app has been used before, then set boot receiver to enabled
        if(!appUsedBefore){


            Log.i(TAG, "app has been used before. setting boot receiver on")

            // It will stay enabled forever until user disables it. We need to do this because if user has
            // never, ever used the app, then it would be unnecessary for the app to start counting the steps.
            val bootReceiver = ComponentName(context , BootReceiver::class.java)

            context.packageManager.setComponentEnabledSetting(
                bootReceiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
        }
    }

    fun setFirstTime(){
        preferencesHelper.setAppFirstUse()
    }

    fun createAlarmManager(){
        appUsedBefore = preferencesHelper.getAppFirstUse()!!
        //Toast.makeText(getApplication(), "has app been used before---> ${appUsedBefore}", Toast.LENGTH_LONG).show()
        //Log.i(TAG, "has app been used before---> ${appUsedBefore}")


        //If app has NEVER been used, then set the alarm once and for all. and set it to used
        if(!appUsedBefore){
            preferencesHelper.setMidnighStepCount(preferencesHelper.getUniversalStepCount()!!)

            Toast.makeText(getApplication(), "virgin app. setting Alarm", Toast.LENGTH_LONG).show()
            Log.i(TAG, "virgin app. setting Alarm")

            // Set the alarm to start at approximately 00:00 p.m as specified in calendar.
            val calendar : Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, 10)
                set(Calendar.MINUTE, 59)
                //TODO dont forget to change hour back to midnight
            }

            alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmPendingIntent = Intent(context, StepsCheckAlarmReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(context, 0, intent, 0)
            }
            var oneDayInMillis =(24L * 60 * 60 *1000)
            var anHour = (60L * 60 *1000)
            var twoMinutes =(2L*60* 1000)
            alarmManager?.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_HOUR,
                alarmPendingIntent
            )

            //TODO What it should do when alarm runs is in the class StepsCheckAlarmReceiver in Utility file
            /*alarmManager?.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                alarmPendingIntent
            )*/

        }
    }

}