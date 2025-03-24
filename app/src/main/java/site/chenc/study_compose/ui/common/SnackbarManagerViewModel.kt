package site.chenc.study_compose.ui.common

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import site.chenc.study_compose.models.SnackbarMessage
import javax.inject.Inject

@HiltViewModel
class SnackbarManagerViewModel @Inject constructor(): ViewModel() {
    private val _snackbarMessages = MutableSharedFlow<SnackbarMessage>()
    val snackbarMessage: SharedFlow<SnackbarMessage> = _snackbarMessages.asSharedFlow()

    fun showSnackbar(message: SnackbarMessage) {
        viewModelScope.launch {
            _snackbarMessages.emit(message)
        }
    }
}