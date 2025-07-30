package com.dailyquotes.app.data.database

import androidx.room.TypeConverter
import com.dailyquotes.app.data.entities.QuoteCategory
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromQuoteCategory(category: QuoteCategory): String {
        return category.name
    }

    @TypeConverter
    fun toQuoteCategory(categoryName: String): QuoteCategory {
        return QuoteCategory.valueOf(categoryName)
    }
}