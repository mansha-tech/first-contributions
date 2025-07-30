package com.dailyquotes.app.di

import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.WorkManager
import com.dailyquotes.app.utils.NotificationScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNotificationScheduler(@ApplicationContext context: Context): NotificationScheduler {
        return NotificationScheduler(context)
    }

    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }
}