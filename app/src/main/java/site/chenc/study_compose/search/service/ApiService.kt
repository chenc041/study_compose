package site.chenc.study_compose.search.service

import retrofit2.http.GET
import retrofit2.http.Path
import site.chenc.study_compose.search.models.UserModel

interface ApiService {
    @GET("users/{name}")
    suspend fun getUser(@Path("name") name: String): UserModel
}