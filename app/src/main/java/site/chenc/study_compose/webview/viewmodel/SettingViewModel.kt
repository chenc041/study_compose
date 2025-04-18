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
class WebViewViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
}