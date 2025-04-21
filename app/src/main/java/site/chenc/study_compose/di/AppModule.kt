package site.chenc.study_compose.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import site.chenc.study_compose.utils.CommonUtils
import site.chenc.study_compose.utils.NotificationUtils
import site.chenc.study_compose.utils.SharedPreferencesUtils
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
    fun provideCommonUtils(@ApplicationContext context: Context): CommonUtils = CommonUtils(context)

    @Provides
    @Singleton
    fun provideSharedPreferencesUtils(@ApplicationContext context: Context): SharedPreferencesUtils = SharedPreferencesUtils(context)

    @Provides
    @Singleton
    fun provideSnackbarManagerUtils(): SnackbarManagerUtils = SnackbarManagerUtils()

}