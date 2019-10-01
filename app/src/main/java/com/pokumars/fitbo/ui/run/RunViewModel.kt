package com.pokumars.fitbo.ui.run

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pokumars.fitbo.BaseViewModel
import com.pokumars.fitbo.util.SharedPreferencesHelper

class RunViewModel(application: Application) : BaseViewModel(application) {
    private var preferencesHelper = SharedPreferencesHelper(getApplication())

    val stride: Float =  0.762f
    var stepsRun: Float = 100f //Todo change this into steps taken during the run
    var distanceInMetres: Float = stride * stepsRun //in metres
    var distanceTravelled: Float = (distanceInMetres/1000)
    val bodyMass:Float = preferencesHelper.getWeight()!!

    var calories: Float =  (distanceTravelled * bodyMass!!)


    fun setWeight(){
        preferencesHelper.setWeight(80f)
    }

}