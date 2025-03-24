package site.chenc.study_compose.setting.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import site.chenc.study_compose.R
import site.chenc.study_compose.utils.CommonUtils
import site.chenc.study_compose.utils.NotificationUtils
import site.chenc.study_compose.utils.SharedPreferencesUtils
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val notificationUtils: NotificationUtils,
    private val commonUtils: CommonUtils,
    private val sharedPreferencesUtils: SharedPreferencesUtils,
    @ApplicationContext private val context: Context
) : ViewModel() {

    fun senNotification() {
        notificationUtils.sendDefaultNotification(
            title = context.getString(R.string.test_notification_title),
            content = context.getString(R.string.test_notification_content),
            iconRes = R.drawable.ic_launcher_foreground
        )
    }

    fun setStringValue(key: String, value: String) {
        sharedPreferencesUtils.saveStringValue(key, value)
    }

    fun getStringValue(key: String): String {
        return sharedPreferencesUtils.getStringValue(key) ?: ""
    }

    fun removeKey(key: String) {
        sharedPreferencesUtils.removeKey(key)
    }

    fun openSettings() {
        commonUtils.openAppSettings()
    }
}