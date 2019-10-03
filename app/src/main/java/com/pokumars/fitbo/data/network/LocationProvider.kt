package com.pokumars.fitbo.data.network

import com.pokumars.fitbo.data.database.Location

interface LocationProvider {
    suspend fun hasLocationChanged(lastLocation:Location):Boolean
    suspend fun getProfferedLocationString():String
}