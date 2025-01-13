package site.chenc.study_compose.search.respository

import site.chenc.study_compose.search.service.ApiService
import site.chenc.study_compose.search.state.UserState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getUser(name: String): UserState {
        return apiService.getUser(name)
    }
}