package com.pokumars.fitbo.data
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Handler
import android.os.IBinder
import android.util.Log
import java.lang.Exception

class StepsService:Service(),SensorEventListener{
    private lateinit var sensorManager: SensorManager
    private  var steps:Sensor? = null
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        //if (BuildConfig.DEBUG) Log.d("Sensor status","SensorListener onCreate")
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        steps = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        sensorManager.registerListener(this,steps,SensorManager.SENSOR_DELAY_UI,Handler())
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d("sensor accuracy change","${sensor}${accuracy}")
    }

    override fun onSensorChanged(event: SensorEvent) {

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        steps = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        sensorManager.registerListener(this,steps,SensorManager.SENSOR_DELAY_UI,Handler())
        return START_STICKY

    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            sensorManager.unregisterListener(this)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    }