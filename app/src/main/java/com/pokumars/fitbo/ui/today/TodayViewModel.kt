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
import com.pokumars.fitbo.util.DeviceBootReceiver
import com.pokumars.fitbo.util.SharedPreferencesHelper
import com.pokumars.fitbo.util.StepsCheckAlarmReceiver
import java.util.*

class TodayViewModel(application: Application) : BaseViewModel(application) {
    private var preferencesHelper =
        SharedPreferencesHelper(getApplication())

    private val context = getApplication<Application>().applicationContext

    val stride: Float =  0.762f
    fun todayStepCount():Float {
        return preferencesHelper.getUniversalStepCount()?.minus(preferencesHelper.getMidnighStepCount()!!)!!
    }

    var distanceInMetres: Float = stride * todayStepCount() //in metres
    var distanceTravelled: Float = (distanceInMetres/1000)
    val bodyMass:Float = preferencesHelper.getWeight()!!

    var calories: Float =  (distanceTravelled * bodyMass)


    private var alarmManager: AlarmManager? = null
    private lateinit var alarmPendingIntent: PendingIntent
    private var appUsedBefore: Boolean = false




    private val _text = MutableLiveData<String>().apply {
        value = (preferencesHelper.getUniversalStepCount()?.minus( preferencesHelper.getMidnighStepCount()!!)).toString()
    }
    val text: LiveData<String> = _text

    fun setBootReceiverEnabled() {
        appUsedBefore = preferencesHelper.getAppFirstUse()!!

        //If app has been used before, then set boot receiver to enabled
        if(appUsedBefore){

            // It will stay enabled forever until user disables it. We need to do this because if user has
            // never, ever used the app, then it would be unnecessary for the app to start counting the steps.
            val bootReceiver = ComponentName(context , DeviceBootReceiver::class.java)

            context.packageManager.setComponentEnabledSetting(
                bootReceiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
            Log.i(TAG, "app has been used before. setting boot receiver on")
        }
    }

    fun setFirstTime(){
        preferencesHelper.setAppFirstUse()
    }

    private fun setWeightToEighty(){
        preferencesHelper.setWeight(80f)
    }

    private fun setDefaultStepsTarget(){
        preferencesHelper.setStepTarget(3000f)
    }
    fun getStepTarget()= preferencesHelper.getStepTarget()

    fun createAlarmManager(){
        appUsedBefore = preferencesHelper.getAppFirstUse()!!

        //If app has NEVER been used, then set the alarm once and for all. and set it to used
        if(!appUsedBefore){
            preferencesHelper.setMidnighStepCount(preferencesHelper.getUniversalStepCount()!!)

            setWeightToEighty()
            setDefaultStepsTarget()

            // Set the alarm to start at approximately 00:00 p.m as specified in calendar.
            val calendar = Calendar.getInstance()
            calendar.setTimeInMillis(System.currentTimeMillis())
            calendar.set(Calendar.HOUR_OF_DAY, 23)
            calendar.set(Calendar.MINUTE, 59)
            calendar.set(Calendar.SECOND, 55)

            Toast.makeText(getApplication(), "virgin app. setting Alarm", Toast.LENGTH_LONG).show()
            Log.i(TAG, "virgin app. setting Alarm. Will happen at ${calendar}")

            alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmPendingIntent = Intent(context, StepsCheckAlarmReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(context, 0, intent, 0)
            }

            val anHour = (60L * 60 *1000)
            val oneDayInMillis =24* anHour
            //val twoMinutes =(2L*60* 1000)
            //val tenMinutes =(10L*60* 1000)
            alarmManager?.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                oneDayInMillis,
                alarmPendingIntent
            )

        }
    }

}