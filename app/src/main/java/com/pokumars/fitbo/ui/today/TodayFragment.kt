package com.pokumars.fitbo.ui.today

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.pokumars.fitbo.R
import com.pokumars.fitbo.ui.TAG
import com.pokumars.fitbo.util.SharedPreferencesHelper
import kotlinx.android.synthetic.main.fragment_today.*

class TodayFragment : Fragment(),SensorEventListener {
    private lateinit var todayViewModel: TodayViewModel

    //StepCounter
    private lateinit var sensorManager:SensorManager
    private  var steps:Sensor? = null
    private var running = false



    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent) {

        val stepsValue =event.values[0]
       if(event.sensor == steps){
           var stepCountString= String.format("%.0f",todayViewModel.todayStepCount())
           //Log.i(TAG, "steps in TodayFrag ------> $stepsValue steps in ")


           todayViewModel.setFirstTime()//turn this on so alarm is never set again
           updateValues()
           text_today.text = "Steps\n\n ${stepCountString}"
           stepsNumberTextView.text = "$stepCountString"
       }
    }

    fun updateValues(){
        todayViewModel.distanceInMetres = todayViewModel.stride * todayViewModel.todayStepCount()
        todayViewModel.distanceTravelled = todayViewModel.distanceInMetres/1000
        todayViewModel.calories =  (todayViewModel.distanceTravelled * todayViewModel.bodyMass!!)
        displayValues()
    }

    fun displayValues(){

        distanceCoveredTextView.setText(resources.getString(R.string.today_page_km, String.format("%.2f",todayViewModel.distanceTravelled)))
        kcalTextView.setText(resources.getString(R.string.today_page_calories, String.format("%.2f",todayViewModel.calories)))
        //runStepsTV.setText(resources.getString(R.string.steps, String.format("%.0f",todayViewModel.todayStepCount())))
        //Log.i(TAG, "----- displayValues()----------")

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
        steps = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        todayViewModel =
            ViewModelProviders.of(this).get(TodayViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_today, container, false)
        val textView: TextView = root.findViewById(R.id.text_today)
        todayViewModel.text.observe(this, Observer {
            textView.text = it
        })

        todayViewModel.setWeight()
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        todayViewModel.createAlarmManager()//setAlarm
        todayViewModel.setFirstTime()//turn this on so alarm is never set again
        todayViewModel.setBootReceiverEnabled()//turn this on so boot receiver will do its part and set new alarm after reboot of phone
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        startRunFragmentBtn.setOnClickListener { view:View ->
            view.findNavController().navigate(TodayFragmentDirections.actionNavigationHomeToRunFragment())
        }

        super.onViewCreated(view, savedInstanceState)

    }

    fun stepsFirstUseSetup(){

    }
}