package site.chenc.study_compose.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import site.chenc.study_compose.utils.NotificationUtils
import site.chenc.study_compose.utils.SnackbarManagerUtils
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNotificationUtils(@ApplicationContext context: Context): NotificationUtils {
        val notificationUtils = NotificationUtils(context)
        notificationUtils.createNotificationChannel()
        return notificationUtils
    }

    @Provides
    @Singleton
    fun provideSnackbarManagerUtils(): SnackbarManagerUtils = SnackbarManagerUtils()
}