package site.chenc.study_compose.setting.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import site.chenc.study_compose.R
import site.chenc.study_compose.utils.NotificationUtils
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val notificationUtils: NotificationUtils
) : ViewModel() {

    fun senNotification() {
        notificationUtils.sendDefaultNotification(
            title = "测试消息通知",
            content = "测试消息通知内容",
            iconRes = R.drawable.ic_launcher_foreground
        )
    }
}