package com.dailyquotes.app.workers

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dailyquotes.app.MainActivity
import com.dailyquotes.app.R
import com.dailyquotes.app.data.entities.QuoteCategory
import com.dailyquotes.app.data.repository.QuoteRepository
import com.dailyquotes.app.data.repository.UserRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class DailyQuoteWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val quoteRepository: QuoteRepository,
    private val userRepository: UserRepository
) : CoroutineWorker(context, workerParams) {

    companion object {
        const val CHANNEL_ID = "daily_quotes_channel"
        const val NOTIFICATION_ID = 1001
    }

    override suspend fun doWork(): Result {
        return try {
            val user = userRepository.getUser().first()
            
            if (user == null || !user.isNotificationsEnabled) {
                return Result.success()
            }

            // Get quote based on user preferences
            val quote = if (user.selectedCategories.isNotEmpty()) {
                val categories = user.selectedCategories.map { QuoteCategory.valueOf(it) }
                quoteRepository.getRandomQuoteByCategories(categories)
            } else {
                quoteRepository.getRandomQuoteByCategory(QuoteCategory.MOTIVATIONAL)
            }

            quote?.let {
                quoteRepository.markQuoteAsShown(it.id)
                showNotification(it.text, it.author)
            }

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    private fun showNotification(quoteText: String, author: String) {
        createNotificationChannel()

        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Daily Quote")
            .setContentText("\"$quoteText\" - $author")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("\"$quoteText\"\n\n- $author")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(applicationContext)
                .notify(NOTIFICATION_ID, notification)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Daily Quotes",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Daily inspirational quotes"
            }

            val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}