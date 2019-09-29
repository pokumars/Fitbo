package com.pokumars.fitbo


import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_run.*

/**
 * A simple [Fragment] subclass.
 */
class RunFragment : Fragment() {

    var timerIsOn= false
    var stopTime: Long = 0
    var finalTime: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_run, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        startRunBtn.setOnClickListener { startTimer() }
        stopRunBtn.setOnClickListener { stopTimer() }
        pauseRunBtn.setOnClickListener { pauseTimer() }
        resumeRunBtn.setOnClickListener { startTimer() }

        if(!timerIsOn){
            stopRunBtn.visibility =View.GONE
            pauseRunBtn.visibility =View.GONE
            resumeRunBtn.visibility =View.GONE
        }
        super.onViewCreated(view, savedInstanceState)
    }


    fun hideButtonsOnCreate(){
        startRunBtn.visibility = View.GONE
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

    fun stopTimer(){
        timerIsOn = true
        stopTime =runTimer.base - SystemClock.elapsedRealtime()

        resetTimer()

        pauseRunBtn.visibility =View.GONE
        resumeRunBtn.visibility = View.GONE
        stopRunBtn.visibility = View.GONE
        startRunBtn.visibility = View.VISIBLE //show the results of the run


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
        //Log.i(TAG, "Final time -------------- ${finalTime.toString()}")
    }

    fun countTheTime(){
        runTimer.base = SystemClock.elapsedRealtime() + stopTime
        runTimer.start()
    }


}
