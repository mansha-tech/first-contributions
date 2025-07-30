package com.dailyquotes.app.di

import android.content.Context
import androidx.room.Room
import com.dailyquotes.app.data.dao.QuoteDao
import com.dailyquotes.app.data.dao.SurveyDao
import com.dailyquotes.app.data.dao.UserDao
import com.dailyquotes.app.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "daily_quotes_database"
        ).build()
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao = database.userDao()

    @Provides
    fun provideQuoteDao(database: AppDatabase): QuoteDao = database.quoteDao()

    @Provides
    fun provideSurveyDao(database: AppDatabase): SurveyDao = database.surveyDao()
}