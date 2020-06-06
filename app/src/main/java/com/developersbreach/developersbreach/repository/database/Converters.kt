package com.developersbreach.developersbreach.repository.database

import androidx.room.TypeConverter
import com.developersbreach.developersbreach.model.Tags
import com.developersbreach.developersbreach.utils.CHECK_WITH_NULL_ASSERTION
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun stringToTagList(string: String?): List<Tags> {
        if (string == null || string == CHECK_WITH_NULL_ASSERTION) {
            return emptyList()
        }
        val listType = object : TypeToken<List<Tags>>() {}.type
        return (Gson()).fromJson(string, listType)
    }

    @TypeConverter
    fun tagListToString(tagList: List<Tags>?): String {
        return Gson().toJson(tagList)
    }
}