package com.pokumars.fitbo.ui.run


import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.pokumars.fitbo.R
import com.pokumars.fitbo.ui.TAG
import kotlinx.android.synthetic.main.fragment_run.*

class RunFragment : Fragment(),SensorEventListener {
    //StepCounter
    private lateinit var sensorManager: SensorManager
    private  var steps:Sensor? = null
    private lateinit var runViewModel: RunViewModel
    //var timerIsOn= false
    private var timerIsOn= false//after view is created becomes--> timerIsOn= runViewModel.getIsTimerOn()!!
    //var stopTime: Long = 0
    private var stopTime: Long = 0
   private var finalTime: Long = 0

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
        stopRunBtn.setOnClickListener {  showExerciseResults();  stopExercise() }
        pauseRunBtn.setOnClickListener { pauseTimer() }
        resumeRunBtn.setOnClickListener { startTimer() }

        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE)as SensorManager
        steps = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        runDistanceTextView.text = resources.getString(R.string.km, String.format("%.2f",runViewModel.distanceTravelled))
        runCaloriesTextView.text = resources.getString(R.string.calories, String.format("%.1f",runViewModel.calories))
        runStepsTV.text= resources.getString(R.string.steps, String.format("%.2f", runViewModel.stepsRun))

        stopTime =runViewModel.getStopTime()!!

        timerIsOn= runViewModel.getIsTimerOn()!!
        if( !timerIsOn){
            hideButtonsOnCreate()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent) {
        if(event.sensor == steps){
            val exerciseStepsValue = (runViewModel.universalSteps()?.minus( runViewModel.exerciseStartSteps()!!))

            updateValues(exerciseStepsValue ?: -1f)
        }
    }

    private fun startExercise(){
        //running = true
        runViewModel.setIsExercising(true)

        steps?.also {
            sensorManager.registerListener(this,it,SensorManager.SENSOR_DELAY_NORMAL)
        }

        //starting step count is already set in the foreground service

        startTimer()
        updateValues(0f)
    }
   private fun stopExercise(){
        //running =false
        runViewModel.setIsExercising(false)

        sensorManager.unregisterListener(this)

        //reset the starting step count to 0 by setting it equal to global step value
        runViewModel.setStartingStepCount()

        stopTimer()
    }

    private fun showExerciseResults(){
        //val minutes = stopTime / 1000 / 60
        //val seconds = -(stopTime / 1000 )% 60

        val minutes = runViewModel.getStopTime()!! / 1000 / 60
        val seconds = -(runViewModel.getStopTime()!! / 1000 )% 60

        val timeExercised = resources.getString(R.string.mins_seconds, minutes.toString(), seconds.toString())
        Log.i(TAG, "stopTimer ---->${runViewModel.getStopTime()!!} Milliseconds = $minutes minutes and $seconds seconds")

        val sCalories= String.format("%.2f", runViewModel.calories)
        val sDistanceTravelled= String.format("%.2f",runViewModel.distanceTravelled)
        val sStepsRun= String.format("%.0f",runViewModel.stepsRun)

        this.findNavController().navigate(RunFragmentDirections
            .actionRunFragmentToEndExerciseFragment(sStepsRun, sDistanceTravelled, sCalories, timeExercised))
        setExerciseValuesToZero()

    }

   private fun displayValues(){

        runDistanceTextView.setText(resources.getString(R.string.km, String.format("%.2f",runViewModel.distanceTravelled)))
        runCaloriesTextView.setText(resources.getString(R.string.calories, String.format("%.1f",runViewModel.calories)))
        runStepsTV.setText(resources.getString(R.string.steps, String.format("%.0f",runViewModel.stepsRun)))
        //Log.i(TAG, "----- displayValues()----------")

    }


    private fun updateValues(newStepValue: Float){
        runViewModel.stepsRun = newStepValue
        //Log.i(TAG, "----- updateValues()----------")

        runViewModel.distanceInMetres = runViewModel.stride * runViewModel.stepsRun
        runViewModel.distanceTravelled = runViewModel.distanceInMetres/1000
        runViewModel.calories =  (runViewModel.distanceTravelled * runViewModel.bodyMass)
        displayValues()
    }


    private fun hideButtonsOnCreate(){//If timer is not on, hide the other buttons
        stopRunBtn.visibility =View.GONE
        pauseRunBtn.visibility =View.GONE
        resumeRunBtn.visibility =View.GONE
    }

    private fun startTimer(){
        runViewModel.setIsTimerOn(true)
        //timerIsOn = true
        countTheTime()
        startRunBtn.visibility = View.GONE
        resumeRunBtn.visibility = View.GONE
        stopRunBtn.visibility = View.GONE
        pauseRunBtn.visibility =View.VISIBLE//only pause is visible
    }



  private  fun stopTimer(){//and then show the results of the run
        //timerIsOn = false
        runViewModel.setIsTimerOn(false)

        resetTimer()

        pauseRunBtn.visibility =View.GONE
        resumeRunBtn.visibility = View.GONE
        stopRunBtn.visibility = View.GONE
        startRunBtn.visibility = View.VISIBLE //show only start Run after
    }

   private fun setExerciseValuesToZero(){
        //If we have to manually set the exercise values to 0. For now the reset onCreate so no need yet
        //runViewModel.wipeExerciseValues()
    }


   private fun pauseTimer(){
        //stopTime =runTimer.base - SystemClock.elapsedRealtime()
        runViewModel.setStopTime(runTimer.base - SystemClock.elapsedRealtime())
        runTimer.stop()

        pauseRunBtn.visibility =View.GONE//resume and stop are visible
        resumeRunBtn.visibility = View.VISIBLE
        stopRunBtn.visibility = View.VISIBLE
    }

   private fun resetTimer(){
        //stopTime =0
        //finalTime = stopTime

        runViewModel.setStopTime(0)
        finalTime = runViewModel.getStopTime()!!

        runTimer.stop()

        //runTimer.base = SystemClock.elapsedRealtime() + stopTime
        runTimer.base = SystemClock.elapsedRealtime() + runViewModel.getStopTime()!!
        Log.i(TAG, "time reset time  should be zero-------------- $finalTime")
    }

    private fun countTheTime(){
        //runTimer.base = SystemClock.elapsedRealtime() + stopTime
        runTimer.base = SystemClock.elapsedRealtime() + runViewModel.getStopTime()!!
        runTimer.start()
    }
}
