package com.dailyquotes.app.data.dao

import androidx.room.*
import com.dailyquotes.app.data.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id = 1")
    fun getUser(): Flow<User?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)
    
    @Update
    suspend fun updateUser(user: User)
    
    @Query("UPDATE users SET hasCompletedOnboarding = :completed WHERE id = 1")
    suspend fun updateOnboardingStatus(completed: Boolean)
    
    @Query("UPDATE users SET isNotificationsEnabled = :enabled WHERE id = 1")
    suspend fun updateNotificationSettings(enabled: Boolean)
    
    @Query("UPDATE users SET selectedCategories = :categories WHERE id = 1")
    suspend fun updateSelectedCategories(categories: List<String>)
}