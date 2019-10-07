package com.pokumars.fitbo.ui.run


import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.pokumars.fitbo.R
import com.pokumars.fitbo.ui.TAG

import kotlinx.android.synthetic.main.fragment_run.*

/**
 * A simple [Fragment] subclass.
 */
class RunFragment : Fragment(),SensorEventListener {
    //StepCounter
    private lateinit var sensorManager: SensorManager
    private  var steps:Sensor? = null
    private var running = false




    private lateinit var runViewModel: RunViewModel


    var timerIsOn= false
    var stopTime: Long = 0
    var finalTime: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        runViewModel =
            ViewModelProviders.of(this).get(RunViewModel::class.java)



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_run, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        startRunBtn.setOnClickListener { startExercise() }
        stopRunBtn.setOnClickListener { stopExercise() }
        pauseRunBtn.setOnClickListener { pauseTimer() }
        resumeRunBtn.setOnClickListener { startTimer() }



        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE)as SensorManager
        steps = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)


        runDistanceTextView.text = resources.getString(R.string.km, String.format("%.2f",runViewModel.distanceTravelled))
        runCaloriesTextView.text = resources.getString(R.string.kcal_burnt, String.format("%.2f",runViewModel.calories))
        runStepsTV.text= resources.getString(R.string.steps, String.format("%.2f", runViewModel.stepsRun))



        if(!timerIsOn){
            hideButtonsOnCreate()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent) {
        val stepsValue =event.values[0]
        if(event.sensor == steps){
            var exerciseStepsValue = (runViewModel.universalSteps()?.minus( runViewModel.exerciseStartSteps()!!))
            var exerciseStepsValue2 = stepsValue - runViewModel.exerciseStartSteps()!!

            updateValues(exerciseStepsValue ?: -1f)
        }
    }

    fun startExercise(){

        running = true
        runViewModel.setIsExercising(true)

        steps?.also {
            sensorManager.registerListener(this,it,SensorManager.SENSOR_DELAY_NORMAL)
        }

        //runViewModel.setStartingStepCount()
        //Log.i(TAG, "Starting step count ${runViewModel.getStartingStepCount()}")
        startTimer()
        updateValues(0f)
    }


    fun resumeExercise(){

    }
    fun pauseExercise(){

    }

    fun stopExercise(){
        running =false
        runViewModel.setIsExercising(false)

        sensorManager.unregisterListener(this)

        val sCalories= String.format("%.2f", runViewModel.calories)
        val sDistanceTravelled= String.format("%.2f",runViewModel.distanceTravelled)
        val sStepsRun= String.format("%.0f",runViewModel.stepsRun)

        //Log.i(TAG, "steps---> $sStepsRun  distance---> $sDistanceTravelled calories---> $sCalories")

        stopTimer()
        this.findNavController().navigate(RunFragmentDirections
            .actionRunFragmentToEndExerciseFragment(sStepsRun, sDistanceTravelled, sCalories))
        setExerciseValuesToZero()
    }

    fun displayValues(){

        runDistanceTextView.setText(resources.getString(R.string.km, String.format("%.2f",runViewModel.distanceTravelled)))
        runCaloriesTextView.setText(resources.getString(R.string.kcal_burnt, String.format("%.2f",runViewModel.calories)))
        runStepsTV.setText(resources.getString(R.string.steps, String.format("%.0f",runViewModel.stepsRun)))
        //Log.i(TAG, "----- displayValues()----------")

    }


    fun updateValues(newStepValue: Float){
        var exerciseStepsString = String.format("%.0f",newStepValue)
        runViewModel.stepsRun = newStepValue
        //Log.i(TAG, "----- updateValues()----------")

        runViewModel.distanceInMetres = runViewModel.stride * runViewModel.stepsRun
        runViewModel.distanceTravelled = runViewModel.distanceInMetres/1000
        runViewModel.calories =  (runViewModel.distanceTravelled * runViewModel.bodyMass!!)
        displayValues()
    }


    fun hideButtonsOnCreate(){//If timer is not on, hide the other buttons
        stopRunBtn.visibility =View.GONE
        pauseRunBtn.visibility =View.GONE
        resumeRunBtn.visibility =View.GONE
    }

    fun startTimer(){
        timerIsOn = true
        countTheTime()
        startRunBtn.visibility = View.GONE
        resumeRunBtn.visibility = View.GONE
        stopRunBtn.visibility = View.GONE
        pauseRunBtn.visibility =View.VISIBLE//only pause is visible
    }

    fun stopTimer(){//and then show the results of the run
        timerIsOn = false
        stopTime =runTimer.base - SystemClock.elapsedRealtime()

        resetTimer()

        pauseRunBtn.visibility =View.GONE
        resumeRunBtn.visibility = View.GONE
        stopRunBtn.visibility = View.GONE
        startRunBtn.visibility = View.VISIBLE //show only start Run after


        //Todo take user to result fragment
    }

    fun setExerciseValuesToZero(){
        //If we have to manually set the exercise values to 0. For now the reset onCreate so no need yet
        //runViewModel.wipeExerciseValues()
    }


    fun pauseTimer(){
        stopTime =runTimer.base - SystemClock.elapsedRealtime()
        runTimer.stop()

        pauseRunBtn.visibility =View.GONE//resume and stop are visible
        resumeRunBtn.visibility = View.VISIBLE
        stopRunBtn.visibility = View.VISIBLE
    }

    fun resetTimer(){
        stopTime =0
        finalTime = stopTime
        runTimer.stop()

        runTimer.base = SystemClock.elapsedRealtime() + stopTime
        Log.i(TAG, "Final time -------------- ${finalTime.toString()}")
    }

    fun countTheTime(){
        runTimer.base = SystemClock.elapsedRealtime() + stopTime
        runTimer.start()
    }
}
