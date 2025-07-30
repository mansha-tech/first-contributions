package com.dailyquotes.app.data.dao

import androidx.room.*
import com.dailyquotes.app.data.entities.SurveyResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface SurveyDao {
    @Query("SELECT * FROM survey_responses WHERE userId = 1")
    fun getSurveyResponse(): Flow<SurveyResponse?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSurveyResponse(response: SurveyResponse)
    
    @Update
    suspend fun updateSurveyResponse(response: SurveyResponse)
}