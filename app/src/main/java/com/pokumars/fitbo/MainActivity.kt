package com.pokumars.fitbo

import android.app.Activity
import android.content.ComponentName
import android.content.pm.PackageManager
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import android.app.PendingIntent
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import java.util.*
const val TAG = "FITBOAPP"
class MainActivity : AppCompatActivity() {

    var alarmManager: AlarmManager? = null
    private lateinit var alarmPendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_suggestion, R.id.navigation_history
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        createAlarmManager()

    }

    fun createAlarmManager(){
        Toast.makeText(this, "setting Alarm", Toast.LENGTH_LONG).show();

        // Set the alarm to start at approximately 00:00 p.m as specified in calendar.
        val calendar : Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 0)
        }

        alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmPendingIntent = Intent(this, StepsCheckAlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(this, 0, intent, 0)
        }

        //TODO What it should do when alarm runs is in the class StepsCheckAlarmReceiver in Utility file
        alarmManager?.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            alarmPendingIntent
        )


    }

    fun setBootReceiverEnabled() {

        // This will set the boot receiver to enabled the first time the user uses the application.
        // It will stay enabled forever until user disables it. We need to do this because if user has
        // never, ever used the app, then it would be unnecessary for the app to start counting the steps.
        val bootReceiver = ComponentName(this , BootReceiver::class.java)

        this.packageManager.setComponentEnabledSetting(
            bootReceiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )

    }
}


