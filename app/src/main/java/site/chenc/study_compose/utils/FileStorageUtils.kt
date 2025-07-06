package site.chenc.study_compose.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import site.chenc.study_compose.models.OperationResult
import java.io.IOException
import javax.inject.Inject


/**
 * 文件相关工具类
 */
class FileStorageUtils @Inject constructor(
    @param:ApplicationContext private val context: Context,
) {
    /**
     * 将 Bitmap 保存到系统相册
     */
    suspend fun saveBitmapToGallery(bitmap: Bitmap, fileName: String = "${System.currentTimeMillis()}.jpg"): OperationResult<Uri?> = withContext(Dispatchers.IO) {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val resolver = context.contentResolver
        var uri: Uri? = null
        try {
            uri = resolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            ) ?: run {
                Log.e("saveBitmapToGallery", "Failed to create MediaStore entry")
                return@withContext OperationResult.Error(Exception("Failed to create MediaStore entry"))
            }
            resolver.openOutputStream(uri)?.use { os ->
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 90, os)) {
                    throw IOException("Failed to compress bitmap")
                }
            }
            contentValues.clear()
            contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
            resolver.update(uri, contentValues, null, null)

            // 通知系统刷新相册
            resolver.notifyChange(uri, null)
            Log.d("saveBitmapToGallery", "Image saved to gallery: $uri")
            OperationResult.Success<Uri?>(uri)
        } catch (e: Exception) {
            Log.e("saveBitmapToGallery", "Error saving image: ${e.message}")
            uri?.let { resolver.delete(it, null, null) }
            OperationResult.Error(e)
        }
    }
}