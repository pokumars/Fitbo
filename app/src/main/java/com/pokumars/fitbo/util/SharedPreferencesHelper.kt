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
        private const val MIDNIGHT_STEP_C0UNT= "midnight step count"
        private const val TODAY_STEP_C0UNT= "today step count"
        private const val EXERCISE_START_STEPS = "exercise start steps"
        private const val USER_WEIGHT= "user weight"
        private const val ISEXERCISING = "is exercising"
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

    fun setIsExercising(value: Boolean){
        prefs?.edit(commit = true){
            putBoolean(ISEXERCISING, value)
        }
    }

    fun getIsExercising() = prefs?.getBoolean(ISEXERCISING,false)

    //Set universal steps
    fun setUniversalStepCount(steps: Float){
        prefs?.edit(commit = true){
            putFloat(UNIVERSAL_STEP_C0UNT, steps)
        }
    }
    //get universal Steps
    fun getUniversalStepCount() = prefs?.getFloat(UNIVERSAL_STEP_C0UNT, 0f)

    //Set steps at Midnight
    fun setMidnighStepCount(steps: Float){
        prefs?.edit(commit = true){
            putFloat(MIDNIGHT_STEP_C0UNT, steps)
        }
    }
    //get Midnight Steps
    fun getMidnighStepCount() = prefs?.getFloat(MIDNIGHT_STEP_C0UNT, -1f)

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

    //Set steps at start of Exercise
    fun setExerciseStartStepCount(steps: Float){
        prefs?.edit(commit = true){
            putFloat(EXERCISE_START_STEPS, steps)
        }
    }
    //get steps at start of Exercise
    fun getExerciseStartStepCount() = prefs?.getFloat(EXERCISE_START_STEPS, -1f)
}