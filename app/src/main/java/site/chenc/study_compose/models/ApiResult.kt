package site.chenc.study_compose.models

sealed class ApiResult<out T>(
    val data: T? = null,
    val statusCode: Int,
    val message: String? = null
)