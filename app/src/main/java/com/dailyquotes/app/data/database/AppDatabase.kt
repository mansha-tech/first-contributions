package com.dailyquotes.app.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.dailyquotes.app.data.dao.QuoteDao
import com.dailyquotes.app.data.dao.SurveyDao
import com.dailyquotes.app.data.dao.UserDao
import com.dailyquotes.app.data.entities.Quote
import com.dailyquotes.app.data.entities.SurveyResponse
import com.dailyquotes.app.data.entities.User

@Database(
    entities = [User::class, Quote::class, SurveyResponse::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun quoteDao(): QuoteDao
    abstract fun surveyDao(): SurveyDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "daily_quotes_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}