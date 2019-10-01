package com.pokumars.fitbo.ui.run


import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.pokumars.fitbo.R
import com.pokumars.fitbo.ui.TAG

import kotlinx.android.synthetic.main.fragment_run.*

/**
 * A simple [Fragment] subclass.
 */
class RunFragment : Fragment() {
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
        runViewModel.setWeight()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_run, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        startRunBtn.setOnClickListener { startTimer() }
        stopRunBtn.setOnClickListener { stopTimer() }
        pauseRunBtn.setOnClickListener { pauseTimer() }
        resumeRunBtn.setOnClickListener { startTimer() }

        button1.setOnClickListener {
            runViewModel.stepsRun= runViewModel.stepsRun +100
            Log.i(TAG, "${runViewModel.stepsRun} steps")
            Log.i(TAG, "${runViewModel.distanceTravelled} km")
            Log.i(TAG, "${runViewModel.calories} calories")

            displayValues()
        }

        runDistanceTextView.text = resources.getString(R.string.km, String.format("%.2f",runViewModel.distanceTravelled))
        runCaloriesTextView.text = resources.getString(R.string.kcal_burnt, String.format("%.2f",runViewModel.calories))
        testStepsTV.text= resources.getString(R.string.steps, String.format("%.2f",runViewModel.stepsRun))


        if(!timerIsOn){
            hideButtonsOnCreate()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    fun displayValues(){
        updateValues()
        runDistanceTextView.setText(resources.getString(R.string.km, String.format("%.2f",runViewModel.distanceTravelled)))
        runCaloriesTextView.setText(resources.getString(R.string.kcal_burnt, String.format("%.2f",runViewModel.calories)))
        testStepsTV.setText(resources.getString(R.string.steps, String.format("%.2f",runViewModel.stepsRun)))

    }

    fun updateValues(){
        runViewModel.stepsRun +=100
        runViewModel.distanceInMetres = runViewModel.stride * runViewModel.stepsRun
        runViewModel.distanceTravelled = runViewModel.distanceInMetres/1000
        runViewModel.calories =  (runViewModel.distanceTravelled * runViewModel.bodyMass!!)
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
