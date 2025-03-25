package site.chenc.study_compose.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import site.chenc.study_compose.models.SnackbarMessage
import site.chenc.study_compose.utils.SnackbarManagerUtils
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val snackbarManagerUtils: SnackbarManagerUtils
): ViewModel() {
    fun showSnackBar(msg: SnackbarMessage) {
        viewModelScope.launch {
            snackbarManagerUtils.showSnackbar(msg)
        }
    }
}