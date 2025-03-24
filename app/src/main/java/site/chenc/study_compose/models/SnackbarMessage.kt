package site.chenc.study_compose.models

import androidx.compose.material3.SnackbarDuration

data class SnackbarMessage(
    val message: String,
    val actionLabel: String? = null,
    val duration: SnackbarDuration = SnackbarDuration.Short,
    val withDismissAction: Boolean = false,
    val onAction: (() -> Unit)? = null,
    val onDismiss: (() -> Unit)? = null
)
