package com.pokumars.fitbo.ui

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.pokumars.fitbo.R
import com.pokumars.fitbo.util.SharedPreferencesHelper
import com.pokumars.fitbo.util.StepsCheckAlarmReceiver
import com.pokumars.fitbo.util.StepsForegroundService
import java.util.*

//const val TAG ="FITBOAPP"
class MainActivity : AppCompatActivity() {

    private lateinit var preferencesHelper: SharedPreferencesHelper

    var alarmManager: AlarmManager? = null
    private var appUsedBefore: Boolean = false
    private lateinit var alarmPendingIntent: PendingIntent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        preferencesHelper =
            SharedPreferencesHelper(this)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_suggestion,
                R.id.navigation_history
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        startStepCounterForegroundService()

    }

    fun startStepCounterForegroundService(){
        //var input = edit_text_input.text.toString()
        //serviceIntent.putExtra("inputExtra", input)

        val serviceIntent = Intent(this, StepsForegroundService::class.java)
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    fun stopService(){// if we need to stop the service
        val serviceIntent = Intent(this, StepsForegroundService::class.java)
        stopService(serviceIntent)

    }

    fun goToExerciseFragment(){
        //when notification is clicked on and exercise is running, take us to the right Fragment
    }

}