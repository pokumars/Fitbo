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
        private const val IS_RUNNING= "is running"
        private const val IS_WALKING="is walking"
        private const val IS_TIMER_ON = "is timer on"
        private const val STOP_TIME= "stop time"
        private const val DAILY_STEP_TARGET = "daily step target"

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

    //Daily step target
    fun setStepTarget(stepTarget: Float){
        prefs?.edit(commit = true){
            putFloat(DAILY_STEP_TARGET, stepTarget)
        }
    }
    fun getStepTarget() = prefs?.getFloat(DAILY_STEP_TARGET,10000f)

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

    fun setIsRunning(yesNo: Boolean){
        prefs?.edit(commit = true){
            putBoolean(IS_RUNNING, yesNo)
        }
    }

    fun getIsRunning() = prefs?.getBoolean(IS_RUNNING, false)

    fun setIsWalking(yesNo: Boolean){
        prefs?.edit(commit = true){
            putBoolean(IS_WALKING, yesNo)
        }
    }

    fun getIsWalking() = prefs?.getBoolean(IS_WALKING, false)

    fun setIsTimerOn(yesNo: Boolean){
        prefs?.edit(commit = true){
            putBoolean(IS_TIMER_ON, yesNo)
        }
    }
    fun getIsTimerOn() = prefs?.getBoolean(IS_TIMER_ON, false)

    fun setStopTime(millisec: Long){
        prefs?.edit(commit = true){
            putLong(STOP_TIME, millisec)
        }
    }
    fun getStopTime() = prefs?.getLong(STOP_TIME, 0L)



}