package com.pokumars.fitbo.util

import android.R
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.pokumars.fitbo.ui.MainActivity
import com.pokumars.fitbo.ui.STEPS_CHANNEL_ID
import com.pokumars.fitbo.ui.TAG

const val FOREGROUND_STEPS = "foreground steps"
class StepsForegroundService:SensorEventListener, Service() {

    private lateinit var sm: SensorManager
    private var stepCounter: Sensor? = null
    var stepCount: Float? = 0f
    private var preferencesHelper =
        SharedPreferencesHelper(this)

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {

        registerStepCounter()
        startStepService(stepCount.toString())
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val input = intent.getStringExtra(FOREGROUND_STEPS)

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )

        val notification = NotificationCompat.Builder(this, STEPS_CHANNEL_ID)
            .setContentTitle("Fitbo")
            .setContentText(input)
            .setSmallIcon(R.drawable.ic_dialog_email)
            .setContentIntent(pendingIntent)
            .setOnlyAlertOnce(true)
            .build()

        startForeground(2, notification)

        //do heavy work on a background thread
        registerStepCounter()
        //stopSelf();

        return START_NOT_STICKY
    }

    fun startStepService(stepCount: String){
        val serviceIntent = Intent(this, StepsForegroundService::class.java)
        serviceIntent.putExtra(FOREGROUND_STEPS, stepCount)

        ContextCompat.startForegroundService(this, serviceIntent);
    }

    fun registerStepCounter(){
        sm = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepCounter = sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)  != null){

            Log.i(TAG, "Log.i --> Step counter really exists  ------------------  Step counter really exists ---------------")
            //println(" println -->Step counter really exists  Step counter really exists")

        }

        stepCounter?.also {
            sm.registerListener(this, it,
                SensorManager.SENSOR_DELAY_NORMAL)
        }
    }



    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        println("accuracy changed ------> accuracy changed ------>  accuracy changed ------> accuracy changed")
    }

    override fun onSensorChanged(p0: SensorEvent?) {

        //TODO put the value in shared pref and then calculate today's steps
        if (p0?.sensor == stepCounter){


            //println("Step counter has been registered")
            //stepsTV.text = getString(R.string.sensor_val,p0?.values?.get(0) ?: -1)
            Log.i(TAG,"step counter value -------------------> ${p0?.values?.get(0) ?: -1}")
            println("step counter value -------------------> ${p0?.values?.get(0) ?: -1}")
            stepCount = p0?.values?.get(0)

            preferencesHelper.setUniversalStepCount(stepCount!!)

            var todayStepCount = (preferencesHelper.getUniversalStepCount()?.minus(preferencesHelper.getMidnighStepCount()!!))
            var stepCountString= resources.getString(com.pokumars.fitbo.R.string.steps, String.format("%.0f",todayStepCount))

            //test to see if this really only changes at midnight.
            Log.i(TAG,"step counter midnight value -------------------> ${preferencesHelper.getMidnighStepCount() ?: -1}")
            startStepService(stepCountString)
        }
    }
}