package com.pokumars.fitbo.ui

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavDestination
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

        //This block makes the bottomNavigationBar invisible in pages that are not top-level
        navController.addOnDestinationChangedListener{ _, page: NavDestination, _->
            if(page.id == R.id.navigation_home || page.id == R.id.navigation_suggestion ||page.id == R.id.navigation_history ){
                navView.visibility = View.VISIBLE
            }else{
                navView.visibility = View.GONE
            }
        }

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


    override fun onBackPressed() {
        //disable the back button when exercise is ongoing. until exercise ends it cant be interrupted
        if(preferencesHelper.getIsExercising()!!){
            //super.onBackPressed()
            Toast.makeText( this,resources.getString(R.string.cannot_exit_run), Toast.LENGTH_LONG).show()
        }else{
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)


        //disable the up button when exercise is ongoing
        if(preferencesHelper.getIsExercising()!!){
            Toast.makeText( this,resources.getString(R.string.cannot_exit_run), Toast.LENGTH_LONG).show()
            return false
        }else{
            return navController.navigateUp()
        }
    }

    fun startStepCounterForegroundService(){
        //var input = edit_text_input.text.toString()
        //serviceIntent.putExtra("inputExtra", input)

        val serviceIntent = Intent(this, StepsForegroundService::class.java)
        ContextCompat.startForegroundService(this, serviceIntent)
    }

    fun stopService(){// if we need to stop the service
        val serviceIntent = Intent(this, StepsForegroundService::class.java)
        stopService(serviceIntent)

    }

    fun goToExerciseFragment(){
        //when notification is clicked on and exercise is running, take us to the right Fragment
    }

}