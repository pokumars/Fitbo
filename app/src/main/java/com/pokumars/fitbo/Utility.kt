package com.pokumars.fitbo

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import java.util.*

class BootReceiver: BroadcastReceiver() {
    //This tells the app that phone has beens witched on

    override fun onReceive(context: Context?, intent: Intent?) {
        //1. When phone is switched off the alarm will switch off. so this turns it back on when phone is on again.
        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {
            // Set the alarm here.
        }
    }
}

class StepsCheckAlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val currentTime = System.currentTimeMillis()
        //E.g
        Toast.makeText(context, "Alarm, Alarm, inReceiver", Toast.LENGTH_LONG).show();
        Log.i(TAG, "Alarm, Alarm, inReceiver ${Date().toString()}")
        println("FITBO Alarm, Alarm, inReceiver println")
        // when received what should it do
        //TODO at this point, check the universal steps and set that
        // in shared preference to use as the 0 level
    }
}