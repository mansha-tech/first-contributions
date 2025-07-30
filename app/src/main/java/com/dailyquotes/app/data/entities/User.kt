package com.dailyquotes.app.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "users")
@Serializable
data class User(
    @PrimaryKey
    val id: Int = 1, // Single user app
    val name: String,
    val preferredTime: String, // Format: "HH:mm"
    val hasCompletedOnboarding: Boolean = false,
    val isNotificationsEnabled: Boolean = true,
    val selectedCategories: List<String> = emptyList() // Serialized as JSON
)