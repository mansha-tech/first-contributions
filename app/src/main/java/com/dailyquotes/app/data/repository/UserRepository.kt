package com.dailyquotes.app.data.repository

import com.dailyquotes.app.data.dao.SurveyDao
import com.dailyquotes.app.data.dao.UserDao
import com.dailyquotes.app.data.entities.SurveyResponse
import com.dailyquotes.app.data.entities.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val surveyDao: SurveyDao
) {
    fun getUser(): Flow<User?> = userDao.getUser()
    
    fun getSurveyResponse(): Flow<SurveyResponse?> = surveyDao.getSurveyResponse()
    
    suspend fun createUser(name: String, preferredTime: String) {
        val user = User(
            name = name,
            preferredTime = preferredTime,
            hasCompletedOnboarding = false,
            isNotificationsEnabled = true
        )
        userDao.insertUser(user)
    }
    
    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }
    
    suspend fun completeOnboarding() {
        userDao.updateOnboardingStatus(true)
    }
    
    suspend fun updateNotificationSettings(enabled: Boolean) {
        userDao.updateNotificationSettings(enabled)
    }
    
    suspend fun updateSelectedCategories(categories: List<String>) {
        userDao.updateSelectedCategories(categories)
    }
    
    suspend fun saveSurveyResponse(response: SurveyResponse) {
        surveyDao.insertSurveyResponse(response)
    }
}