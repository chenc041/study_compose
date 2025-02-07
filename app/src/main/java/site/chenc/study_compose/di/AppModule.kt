package site.chenc.study_compose.di

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import site.chenc.study_compose.utils.NotificationUtils
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSnackBarHostState(): SnackbarHostState {
        return SnackbarHostState()
    }

    @Provides
    @Singleton
    fun provideNotificationUtils(@ApplicationContext context: Context): NotificationUtils {
        val notificationUtils = NotificationUtils(context)
        notificationUtils.createNotificationChannel()
        return notificationUtils
    }
}