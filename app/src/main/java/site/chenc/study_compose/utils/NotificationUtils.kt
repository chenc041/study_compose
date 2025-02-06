package site.chenc.study_compose.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

object NotificationUtils {
    private const val CHANNEL_ID = "study_compose_channel"
    private const val CHANNEL_NAME = "study_compose_channel_name"

    fun createNotificationChannel(context: Context) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Default notifications for the app"
        }
        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    // 发送通知
    fun sendNotification(
        context: Context,
        title: String,
        content: String,
        @DrawableRes iconRes: Int,
        pendingIntent: PendingIntent? = null,
    ) {
        // 构建通知
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(iconRes)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)

        pendingIntent?.let { builder.setContentIntent(it) }

        // 发送通知
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            with(NotificationManagerCompat.from(context)) {
                notify(getUniqueId(), builder.build())
            }
        }
    }

    private fun getUniqueId(): Int = System.currentTimeMillis().toInt()
}