package site.chenc.study_compose.webview.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class WebViewViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context
) : ViewModel() {
}