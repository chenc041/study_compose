package site.chenc.study_compose.models

sealed class OperationResult<out T> {
    data class Success<T>(val data: T? = null) : OperationResult<T>()
    data class Error(val exception: Throwable) : OperationResult<Nothing>()
}
