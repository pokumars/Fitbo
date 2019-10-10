package com.pokumars.fitbo.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object Converter{

    @TypeConverter
    @JvmStatic
    fun fromString(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    @JvmStatic
    fun fromListList(list: List<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}