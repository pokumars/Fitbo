package com.pokumars.fitbo.ui.today

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pokumars.fitbo.R
import kotlinx.android.synthetic.main.fragment_today.*

class TodayFragment : Fragment(),SensorEventListener {
    private lateinit var todayViewModel: TodayViewModel
    private lateinit var sensorManager:SensorManager
    private  var steps:Sensor? = null
    private var running = false
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent) {
        val stepsValue =event.values[0]
       if(event.sensor == steps){

           text_today.text = "Steps\n\n $stepsValue"
       }
    }

    override fun onResume() {
        super.onResume()
        running = true
        steps?.also {
            sensorManager.registerListener(this,it,SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        running =false
        sensorManager.unregisterListener(this)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE)as SensorManager
         steps = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        todayViewModel =
            ViewModelProviders.of(this).get(TodayViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_today, container, false)
        val textView: TextView = root.findViewById(R.id.text_today)
        todayViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}