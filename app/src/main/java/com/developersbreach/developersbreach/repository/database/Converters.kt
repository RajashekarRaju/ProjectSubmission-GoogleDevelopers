package com.developersbreach.developersbreach.repository.database

import androidx.room.TypeConverter
import com.developersbreach.developersbreach.model.Tags
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun stringToTagList(string: String?): List<Tags> {
        if (string == null || string == "null") return emptyList()
        val listType = object : TypeToken<List<Tags>>() {}.type
        return (Gson()).fromJson(string, listType)
    }

    @TypeConverter
    fun tagListToString(tagList: List<Tags>?): String {
        return Gson().toJson(tagList)
    }
}