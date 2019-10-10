package com.pokumars.fitbo.ui.history

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pokumars.fitbo.BaseViewModel
import com.pokumars.fitbo.util.SharedPreferencesHelper

class HistoryViewModel (application: Application): BaseViewModel(application) {
    private var preferencesHelper =
        SharedPreferencesHelper(getApplication())

    val stride: Float =  0.762f
    fun universalStepCount():Float {
        return preferencesHelper.getUniversalStepCount()!!
    }

    var distanceInMetres: Float = stride * universalStepCount() //in metres
    var distanceTravelled: Float = (distanceInMetres/1000)
    val bodyMass:Float = preferencesHelper.getWeight()!!

    var calories: Float =  (distanceTravelled * bodyMass)

    private val _text = MutableLiveData<String>().apply {
        value = "This is history Fragment"
    }
    val text: LiveData<String> = _text

}