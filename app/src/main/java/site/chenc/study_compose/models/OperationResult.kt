package site.chenc.study_compose.models

sealed class OperationResult {
    data class Success(val data: Any? = null) : OperationResult()
    data class Error(val exception: Exception) : OperationResult()
}
