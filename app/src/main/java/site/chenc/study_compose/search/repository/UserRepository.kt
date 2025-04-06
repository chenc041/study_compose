package site.chenc.study_compose.search.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import site.chenc.study_compose.search.models.UserModel
import site.chenc.study_compose.search.service.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val apiService: ApiService
) {
     fun getUser(name: String): Flow<UserModel> = flow {
        val response = apiService.getUser(name)
         emit(response)
    }
}