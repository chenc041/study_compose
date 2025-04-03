package site.chenc.study_compose.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import site.chenc.study_compose.models.SnackbarMessage
import site.chenc.study_compose.utils.SnackbarManagerUtils
import javax.inject.Inject

@HiltViewModel
class GlobalViewModel @Inject constructor(
    private val snackbarManagerUtils: SnackbarManagerUtils
): ViewModel() {
    val snackbarMessage get() = snackbarManagerUtils.snackbarMessage

    fun showSnackbar(message: SnackbarMessage) {
        viewModelScope.launch {
            snackbarManagerUtils.showSnackbar(message)
        }
    }
}