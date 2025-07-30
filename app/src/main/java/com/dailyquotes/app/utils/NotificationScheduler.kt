package com.dailyquotes.app.utils

import android.content.Context
import androidx.work.*
import com.dailyquotes.app.workers.DailyQuoteWorker
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationScheduler @Inject constructor(
    private val context: Context
) {
    companion object {
        const val DAILY_QUOTE_WORK_NAME = "daily_quote_work"
    }

    fun scheduleDaily(timeString: String) {
        val workManager = WorkManager.getInstance(context)
        
        // Cancel existing work
        workManager.cancelUniqueWork(DAILY_QUOTE_WORK_NAME)
        
        // Parse time
        val time = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"))
        val now = LocalTime.now()
        
        // Calculate initial delay
        val initialDelay = if (time.isAfter(now)) {
            time.toSecondOfDay() - now.toSecondOfDay()
        } else {
            // Schedule for next day
            (24 * 60 * 60) - now.toSecondOfDay() + time.toSecondOfDay()
        }

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresBatteryNotLow(false)
            .build()

        val dailyWorkRequest = PeriodicWorkRequestBuilder<DailyQuoteWorker>(24, TimeUnit.HOURS)
            .setConstraints(constraints)
            .setInitialDelay(initialDelay.toLong(), TimeUnit.SECONDS)
            .build()

        workManager.enqueueUniquePeriodicWork(
            DAILY_QUOTE_WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            dailyWorkRequest
        )
    }

    fun cancelDaily() {
        WorkManager.getInstance(context).cancelUniqueWork(DAILY_QUOTE_WORK_NAME)
    }
}