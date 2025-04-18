package site.chenc.study_compose.interceptors

import android.util.Log
import javax.inject.Inject
import okhttp3.Interceptor
import site.chenc.study_compose.AppConfig
import site.chenc.study_compose.utils.SharedPreferencesUtils

/**
 * auth 拦截器，header 添加权限参数
 */
class AuthInterceptor @Inject constructor(private val sharedPreferencesUtils: SharedPreferencesUtils): Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val token = sharedPreferencesUtils.getStringValue(AppConfig.AUTH_TOKEN_KEY) ?: ""
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(request)
    }
}