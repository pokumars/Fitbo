package com.pokumars.fitbo.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LocationDao{
    @Insert(onConflict= OnConflictStrategy.REPLACE)
    fun upsert(location: Location)

    @Query("select * from current_location where id =$LOCATION_ID")
    fun getLocation(): LiveData<Location>
    /*@Query("select * from currentLocationDao where id =$LOCATION_ID")
    fun getLocationNonLive(): Location?*/
}