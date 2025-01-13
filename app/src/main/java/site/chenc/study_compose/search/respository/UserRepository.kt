package site.chenc.study_compose.search.respository

import site.chenc.study_compose.search.models.UserModel
import site.chenc.study_compose.search.service.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getUser(name: String): UserModel {
        return apiService.getUser(name)
    }
}