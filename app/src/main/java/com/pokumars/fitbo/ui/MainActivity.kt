package com.pokumars.fitbo.ui

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.pokumars.fitbo.R
import com.pokumars.fitbo.util.StepsForegroundService

const val TAG ="FITBOAPP"
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


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

    fun stopService(){
        val serviceIntent = Intent(this, StepsForegroundService::class.java)
        stopService(serviceIntent)

    }

    fun goToExerciseFragment(){
        //when notification is clicked on and exercise is running, take us to the right Fragment
    }

}


