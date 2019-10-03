package com.pokumars.fitbo.data.database


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

const val LOCATION_ID = 1
@Entity(tableName = "current_location")
data class Location(
    @SerializedName("country")
    val country: String,
    @SerializedName("lat")
    val lat: String,
    @SerializedName("localtime")
    val localtime: String,
    @SerializedName("localtime_epoch")
    val localtimeEpoch: Int,
    @SerializedName("lon")
    val lon: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("region")
    val region: String,
    @SerializedName("timezone_id")
    val timezoneId: String,
    @SerializedName("utc_offset")
    val utcOffset: String
){
    @PrimaryKey(autoGenerate = false)
    var id:Int = LOCATION_ID

    val zonedDateTime:ZonedDateTime
    get() {
        val instant = Instant.ofEpochSecond(localtimeEpoch.toLong())
        val zoneId = ZoneId.of(timezoneId)
        return ZonedDateTime.ofInstant(instant,zoneId)
    }
}