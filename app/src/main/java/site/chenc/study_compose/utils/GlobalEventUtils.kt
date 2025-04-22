package site.chenc.study_compose.utils

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import site.chenc.study_compose.models.SnackbarMessage

/**
 * 全局 事件总线
 */
object GlobalEventUtils {

    /**
     * 显示 Snackbar
     */
    private val _snackbarMessages = MutableSharedFlow<SnackbarMessage>(
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val snackbarMessage: SharedFlow<SnackbarMessage> = _snackbarMessages.asSharedFlow()


    /**
     * 是否鉴权
     */
    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated = _isAuthenticated.asStateFlow()

    fun updateIsAuthenticated(isAuthenticated: Boolean) {
        _isAuthenticated.value = isAuthenticated
    }

    /**
     * 显示 snackbar
     */
    suspend fun showSnackbar(message: SnackbarMessage) {
        _snackbarMessages.emit(message)
    }

}