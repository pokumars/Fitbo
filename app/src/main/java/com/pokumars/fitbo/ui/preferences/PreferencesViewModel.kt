package com.pokumars.fitbo.ui.preferences

import android.app.Application
import androidx.lifecycle.ViewModel
import com.pokumars.fitbo.BaseViewModel
import com.pokumars.fitbo.util.SharedPreferencesHelper

class PreferencesViewModel(application: Application) : BaseViewModel(application){
    private var preferencesHelper =
        SharedPreferencesHelper(getApplication())

    fun setWeight(w:Float){
        preferencesHelper.setWeight(w)
    }
    fun getWeight() = preferencesHelper.getWeight()


    fun setDailyStepTarget(stepTarget: Float){
        preferencesHelper.setStepTarget(stepTarget)
    }
    fun getDailyStepTarget() = preferencesHelper.getStepTarget()

}