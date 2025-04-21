package site.chenc.study_compose.interceptors

import okhttp3.Interceptor
import site.chenc.study_compose.AppConfig
import site.chenc.study_compose.utils.GlobalEventBusUtils
import site.chenc.study_compose.utils.SharedPreferencesUtils
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.net.ssl.SSLHandshakeException

/**
 * auth 拦截器，header 添加权限参数
 */
class AuthInterceptor @Inject constructor(
    private val sharedPreferencesUtils: SharedPreferencesUtils,
    private val globalEventBusUtils: GlobalEventBusUtils,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val token = sharedPreferencesUtils.getStringValue(AppConfig.AUTH_TOKEN_KEY) ?: ""
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        try {
            val response = chain.proceed(request)
            if (response.code == 401) {
                globalEventBusUtils.updateIsAuthenticated(false)
            }
            return response
        } catch (e: IOException) {
            when (e) {
                is SSLHandshakeException -> {
                    throw e
                }
                is SocketTimeoutException -> {
                    throw e
                }
                else -> {
                    throw e
                }
            }
        }
    }
}