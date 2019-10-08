package com.pokumars.fitbo.ui.run

import android.app.Application
import android.util.Log
import com.pokumars.fitbo.BaseViewModel
import com.pokumars.fitbo.ui.TAG
import com.pokumars.fitbo.util.SharedPreferencesHelper

class RunViewModel(application: Application) : BaseViewModel(application) {
    private var preferencesHelper = SharedPreferencesHelper(getApplication())

    val stride: Float =  0.762f
    var stepsRun: Float = 0f //Todo change this into steps taken during the run

    var distanceInMetres: Float = stride * stepsRun //in metres
    var distanceTravelled: Float = (distanceInMetres/1000)
    val bodyMass:Float = preferencesHelper.getWeight()!!

    fun exerciseStartSteps()= preferencesHelper.getExerciseStartStepCount()

    fun universalSteps() = preferencesHelper.getUniversalStepCount()

    fun setStartingStepCount(){

        var currentSteps = preferencesHelper.getUniversalStepCount()
        preferencesHelper.setExerciseStartStepCount(currentSteps ?: 0f)
        Log.i(TAG, "UniversalStepCount --> ${preferencesHelper.getUniversalStepCount()} setExerciseStartStepCount --> ${preferencesHelper.getExerciseStartStepCount()}")
    }

    fun setIsExercising(trueOrFalse:Boolean){
        preferencesHelper.setIsExercising(trueOrFalse)
    }


    fun getStartingStepCount()= preferencesHelper.getExerciseStartStepCount()



    var calories: Float =  (distanceTravelled * bodyMass!!)



}