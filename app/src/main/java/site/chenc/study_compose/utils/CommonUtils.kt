package site.chenc.study_compose.utils

import android.content.Context
import android.content.Intent
import android.provider.Settings
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import site.chenc.study_compose.models.OperationResult
import androidx.core.net.toUri

class CommonUtils @Inject constructor(
    @ApplicationContext private val context: Context
) {
    /**
     * 打开应用设置页面
     */
    fun openAppSettings(): OperationResult {
        return try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = "package:${context.packageName}".toUri()
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            OperationResult.Success
        } catch (e: Exception) {
            OperationResult.Error(e)
        }
    }
}