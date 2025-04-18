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
import dagger.hilt.android.qualifiers.ApplicationContext
import site.chenc.study_compose.AppConfig
import site.chenc.study_compose.R
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

class NotificationUtils @Inject constructor(
   @ApplicationContext private val context: Context
) {

    fun createNotificationChannel() {
        val manager = context.getSystemService(NotificationManager::class.java)
        if (manager.getNotificationChannel(AppConfig.DEFAULT_CHANNEL_ID) == null) {
            val defaultChannelName = context.resources.getString(R.string.default_channel_name)
            val defaultDescription = context.resources.getString(R.string.default_channel_description)
            val channel = NotificationChannel(
                AppConfig.DEFAULT_CHANNEL_ID,
                defaultChannelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = defaultDescription
            }
            manager.createNotificationChannel(channel)
        }
    }

    /**
     * 发送消息通知
     */
    fun sendDefaultNotification(
        title: String,
        content: String,
        @DrawableRes iconRes: Int,
        pendingIntent: PendingIntent? = null,
    ) {
        // 构建通知
        val builder = NotificationCompat.Builder(context, AppConfig.DEFAULT_CHANNEL_ID)
            .setSmallIcon(iconRes)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        pendingIntent?.let { builder.setContentIntent(it) }

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

    /**
     * 获取唯一的id
     */
    private fun getUniqueId(): Int = notificationId.getAndIncrement()
}