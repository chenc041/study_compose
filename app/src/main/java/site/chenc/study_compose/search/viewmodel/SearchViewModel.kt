package site.chenc.study_compose.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import okio.IOException
import retrofit2.HttpException
import site.chenc.study_compose.models.UiState
import site.chenc.study_compose.search.respository.UserRepository
import site.chenc.study_compose.search.models.UserModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userState = MutableStateFlow<UiState<UserModel>>(UiState.Idle)
    val userState: StateFlow<UiState<UserModel>> = _userState.asStateFlow()
    private var currentRequestJob: Job? = null
    fun fetchUser(name: String) {
        currentRequestJob?.cancel()
        _userState.update {
            UiState.Loading
        }
        currentRequestJob = userRepository.getUser(name)
            .onEach { user ->
                _userState.update {
                    UiState.Success(user)
                }
            }.catch { exception ->
                val errorMessage = when(exception) {
                    is IOException -> "网络错误"
                    is HttpException -> "请求错误, 状态码 ${exception.code()}"
                    else -> exception.message ?: "未知错误"
                }
                _userState.update {
                    UiState.Error(errorMessage)
                }

            }.launchIn(viewModelScope)
    }
}