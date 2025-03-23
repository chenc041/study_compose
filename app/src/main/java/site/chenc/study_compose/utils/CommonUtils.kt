package site.chenc.study_compose.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.core.net.toUri
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.DetectedObject
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import site.chenc.study_compose.models.OperationResult
import java.io.IOException
import androidx.exifinterface.media.ExifInterface
import android.graphics.*
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommonUtils @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fileStorageUtils: FileStorageUtils
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

    /**
     * 对象检测
     */
    @OptIn(ExperimentalGetImage::class)
    fun objectDetection(uri: Uri) {
        val options = ObjectDetectorOptions.Builder()
            .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
            .enableClassification()
            .build()
        val objectDetector = ObjectDetection.getClient(options)
        val image = InputImage.fromFilePath(context, uri)
        objectDetector.process(image)
            .addOnSuccessListener { detectionResults ->
                CoroutineScope(Dispatchers.IO).launch {
                    drawBoundingBoxAndSave(uri, detectionResults)
                }
            }
            .addOnFailureListener { e ->
                objectDetector.close()
            }
            .addOnCompleteListener {
                objectDetector.close()
            }
    }

    fun textRecognition(uri: Uri) {
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        val image = InputImage.fromFilePath(context, uri)
        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                // Task completed successfully
                // ...
            }
            .addOnFailureListener { e ->
                // Task failed with an exception
                // ...
            }
            .addOnCompleteListener {
                recognizer.close()
            }
    }
    /**
     * 在图片上绘制检测框并保存到相册
     * @param uri 原始图片的 Uri
     * @param detectionResults 检测结果列表
     */
    fun drawBoundingBoxAndSave(uri: Uri, detectionResults: List<DetectedObject>) {
        // 1. 获取旋转后的 Bitmap（处理 Exif 方向）
        val rotatedBitmap = getRotatedBitmapFromUri(uri) ?: run {
            Log.e("Draw", "Failed to load bitmap")
            return
        }

        // 2. 创建可修改的 Bitmap 副本
        val mutableBitmap = rotatedBitmap.copy(Bitmap.Config.ARGB_8888, true)
        val mutableCropBitmap = rotatedBitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(mutableBitmap)
        val paint = Paint().apply {
            color = Color.RED       // 边框颜色
            style = Paint.Style.STROKE
            strokeWidth = 10f        // 边框宽度
        }

        // 3. 绘制所有检测框
        detectionResults.forEach { obj ->
            val boundingBox = obj.boundingBox
            val croppedBitmap =  cropBitmapWithBoundingBox(mutableBitmap, boundingBox)
            croppedBitmap?.let {
                fileStorageUtils.saveBitmapToGallery(it)
                it.recycle()
            }
            canvas.drawRect(boundingBox, paint)
        }

        // 4. 保存到相册
        fileStorageUtils.saveBitmapToGallery(mutableBitmap)
    }

    /**
     * 从 Uri 加载 Bitmap 并处理 Exif 旋转
     */
    fun getRotatedBitmapFromUri(uri: Uri): Bitmap? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            // 读取 Exif 旋转信息
            val exif = ExifInterface(inputStream!!)
            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
            val rotationDegrees = when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> 90
                ExifInterface.ORIENTATION_ROTATE_180 -> 180
                ExifInterface.ORIENTATION_ROTATE_270 -> 270
                else -> 0
            }
            inputStream.close()

            // 重新加载 Bitmap 并旋转
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = false
                inPreferredConfig = Bitmap.Config.ARGB_8888
            }
            val bitmap = BitmapFactory.decodeStream(
                context.contentResolver.openInputStream(uri),
                null,
                options
            ) ?: return null

            // 应用旋转矩阵
            val matrix = Matrix()
            matrix.postRotate(rotationDegrees.toFloat())
            Bitmap.createBitmap(
                bitmap, 0, 0,
                bitmap.width, bitmap.height,
                matrix, true
            )
        } catch (e: IOException) {
            Log.e("RotateBitmap", "Error rotating bitmap: ${e.message}")
            null
        }
    }

    /**
     * 根据 boundingBox 裁剪 Bitmap
     * @return 裁剪后的 Bitmap 或 null（若区域无效）
     */
    private fun cropBitmapWithBoundingBox(
        bitmap: Bitmap,
        boundingBox: Rect
    ): Bitmap? {
        // 校验 boundingBox 有效性
        return if (boundingBox.left >= 0 &&
            boundingBox.top >= 0 &&
            boundingBox.right <= bitmap.width &&
            boundingBox.bottom <= bitmap.height
        ) {
            Bitmap.createBitmap(
                bitmap,
                boundingBox.left,
                boundingBox.top,
                boundingBox.width(),
                boundingBox.height()
            )
        } else {
            Log.e("Crop", "Invalid bounding box: $boundingBox")
            null
        }
    }


}