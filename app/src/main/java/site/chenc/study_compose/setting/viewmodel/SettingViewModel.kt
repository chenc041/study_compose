package site.chenc.study_compose.setting.viewmodel

import android.content.Context
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import site.chenc.study_compose.R
import site.chenc.study_compose.utils.NotificationUtils
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val notificationUtils: NotificationUtils,
    @ApplicationContext private val context: Context
) : ViewModel() {

    fun senNotification() {
        notificationUtils.sendDefaultNotification(
            title = context.getString(R.string.test_notification_title),
            content = context.getString(R.string.test_notification_content),
            iconRes = R.drawable.ic_launcher_foreground
        )
    }
}