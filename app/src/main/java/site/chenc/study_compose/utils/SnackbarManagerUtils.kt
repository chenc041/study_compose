package site.chenc.study_compose.utils

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import site.chenc.study_compose.models.SnackbarMessage
import javax.inject.Inject

class SnackbarManagerUtils @Inject constructor() {
    private val _snackbarMessages = MutableSharedFlow<SnackbarMessage>(
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val snackbarMessage: SharedFlow<SnackbarMessage> = _snackbarMessages.asSharedFlow()

    suspend fun showSnackbar(message: SnackbarMessage) {
        _snackbarMessages.emit(message)
    }
}