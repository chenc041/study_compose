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
import site.chenc.study_compose.R
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

class NotificationUtils @Inject constructor(
    private val context: Context
) {

    fun createNotificationChannel() {
        val defaultChannelId = context.resources.getString(R.string.default_channel_id)
        val defaultChannelName = context.resources.getString(R.string.default_channel_name)
        var defaultDescription = context.resources.getString(R.string.default_channel_description)
        val channel = NotificationChannel(
            defaultChannelId,
            defaultChannelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = defaultDescription
        }
        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    // 发送通知
    fun sendDefaultNotification(
        title: String,
        content: String,
        @DrawableRes iconRes: Int,
        pendingIntent: PendingIntent? = null,
    ) {
        // 构建通知
        val defaultChannelId = context.resources.getString(R.string.default_channel_id)
        val builder = NotificationCompat.Builder(context, defaultChannelId)
            .setSmallIcon(iconRes)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
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
    private val notificationId = AtomicInteger(0)
    fun getUniqueId(): Int = notificationId.getAndIncrement()
}