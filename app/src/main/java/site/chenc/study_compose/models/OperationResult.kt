package site.chenc.study_compose.models

sealed class OperationResult {
    object Success : OperationResult()
    data class Error(val exception: Exception) : OperationResult()
}
