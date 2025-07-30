package com.dailyquotes.app.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "quotes")
@Serializable
data class Quote(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val text: String,
    val author: String,
    val category: QuoteCategory,
    val isFavorite: Boolean = false,
    val lastShown: Long? = null // Timestamp when last shown
)

enum class QuoteCategory(val displayName: String) {
    MOTIVATIONAL("Motivational"),
    HUMOR("Humor"),
    PRODUCTIVITY("Productivity"),
    RELIGIOUS("Religious"),
    WELLNESS("Wellness"),
    SUCCESS("Success"),
    WISDOM("Wisdom"),
    LOVE("Love"),
    AFFIRMATIONS("Daily Affirmations")
}