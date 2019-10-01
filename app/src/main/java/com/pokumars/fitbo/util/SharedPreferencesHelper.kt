package com.pokumars.fitbo.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager


/*class SharedPreferencesHelper {
}*/

class SharedPreferencesHelper {

    companion object{
        private const val APP_USED_BEFORE ="app used before"
        private const val UNIVERSAL_STEP_C0UNT= "universal step count"
        private const val USER_WEIGHT= "user weight"
        private var prefs: SharedPreferences? = null

        @Volatile private var instance: SharedPreferencesHelper? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): SharedPreferencesHelper = instance
            ?: synchronized(
                LOCK
            ){
            instance
                ?: buildHelper(context).also{
                instance = it
            }
        }

        private fun buildHelper(context: Context): SharedPreferencesHelper {
            prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPreferencesHelper()
        }

    }

    //Set steps
    fun setUniversalStepCount(steps: Int){
        prefs?.edit(commit = true){
            putInt(UNIVERSAL_STEP_C0UNT, steps)
        }
    }
    //get Steps
    fun getUniversalStepCount() = prefs?.getInt(
        UNIVERSAL_STEP_C0UNT, 0)

    //Set first use
    fun setAppFirstUse(){
        //if app has never been used before, appUsedBefore value is false.
        //This sets it to true on first use
        prefs?.edit(commit = true){
            putBoolean(APP_USED_BEFORE, true)
        }
    }

    fun getAppFirstUse()= prefs?.getBoolean(APP_USED_BEFORE, false)

    //setWeight
    fun setWeight(weight: Float){
        prefs?.edit(commit = true){
            putFloat(USER_WEIGHT, weight)
        }
    }

    fun getWeight ()= prefs?.getFloat(USER_WEIGHT, 80f)


}