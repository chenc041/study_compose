package site.chenc.study_compose.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * 全局 事件总线
 */
class GlobalEventBusUtils @Inject constructor() {

    /**
     * 是否登录
     */
    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated = _isAuthenticated.asStateFlow()

    fun updateIsAuthenticated(isAuthenticated: Boolean) {
        _isAuthenticated.value = isAuthenticated
    }
}