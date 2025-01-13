package site.chenc.study_compose.search.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import site.chenc.study_compose.search.respository.UserRepository
import site.chenc.study_compose.search.state.UserState

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    // ViewModel 逻辑

    private val _user = MutableStateFlow<UserState?>(null)
    val user: StateFlow<UserState?> get() = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error


    init {
        println("init success ===> $userRepository");
    }

    fun fetchUser(name: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = userRepository.getUser(name)
                _user.value = response
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}