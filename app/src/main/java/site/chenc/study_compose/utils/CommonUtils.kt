package site.chenc.study_compose.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.annotation.Nullable
import androidx.core.net.toUri
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.suspendCancellableCoroutine
import site.chenc.study_compose.models.OperationResult
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class CommonUtils @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    private val barcodeScanner by lazy {
        BarcodeScanning.getClient(
            BarcodeScannerOptions.Builder().setBarcodeFormats(
                Barcode.FORMAT_QR_CODE
            ).build()
        )
    }

    /**
     * 打开应用设置页面
     */
    fun openAppSettings(): OperationResult<Nullable> {
        return try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = "package:${context.packageName}".toUri()
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            OperationResult.Success(null)
        } catch (e: Exception) {
            OperationResult.Error(e)
        }
    }

    /**
     * 扫码识别
     * @param uri 图片的 Uri
     * @return 识别结果
     */
    suspend fun barcodeScanning(uri: Uri): List<Barcode> {
        return suspendCancellableCoroutine { continuation ->
            try {
                val image = InputImage.fromFilePath(context, uri)
                barcodeScanner.process(image)
                    .addOnSuccessListener { barcodes ->
                        continuation.resume(barcodes)
                    }
                    .addOnFailureListener { e ->
                        continuation.resumeWithException(e)
                    }
            } catch (e: IOException) {
                continuation.resumeWithException(e)
            }
        }
    }

}