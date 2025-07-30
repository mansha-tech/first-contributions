package com.dailyquotes.app.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "survey_responses")
@Serializable
data class SurveyResponse(
    @PrimaryKey
    val userId: Int = 1,
    val motivationLevel: Int, // 1-5 scale
    val stressLevel: Int, // 1-5 scale
    val mainGoals: List<String>, // Life goals
    val currentChallenges: List<String>, // Current challenges
    val preferredTone: String, // "uplifting", "serious", "humorous", etc.
    val responseDate: Long = System.currentTimeMillis()
)