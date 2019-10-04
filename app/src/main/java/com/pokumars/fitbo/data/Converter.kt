package com.pokumars.fitbo.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object Converter{

    @TypeConverter
    @JvmStatic
    fun saveList(listOfString: List<String>): String {
        return Gson().toJson(listOfString)
    }
    @TypeConverter
    @JvmStatic
    fun restoreList(listOfString: String): List<String> {
        return   listOf("we1","w2")/*Gson().fromJson(listOfString, object : TypeToken<List<String>>() {

        }.getType())*/
    }


}