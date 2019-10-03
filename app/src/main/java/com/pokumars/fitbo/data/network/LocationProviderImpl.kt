package com.pokumars.fitbo.data.network

import com.pokumars.fitbo.data.database.Location

class LocationProviderImpl : LocationProvider {
    override suspend fun hasLocationChanged(lastLocation: Location): Boolean {
        return true
    }

    override suspend fun getProfferedLocationString(): String {
        return "vantaa"
    }
}