package site.chenc.study_compose.setting.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import android.Manifest
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.accompanist.permissions.isGranted
import site.chenc.study_compose.setting.viewmodel.SettingViewModel
import androidx.camera.core.Preview
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

@OptIn(ExperimentalPermissionsApi::class, ExperimentalPermissionsApi::class)
@Composable
fun QRCodeScannerScreen(navController: NavController, settingViewModel: SettingViewModel = hiltViewModel<SettingViewModel>()) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }
    var previewUseCase by remember { mutableStateOf<Preview?>(null) }
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA);


    if (cameraPermissionState.status.isGranted) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AndroidView(
                factory = { ctx ->
                    PreviewView(ctx).apply {
                        cameraProviderFuture.addListener({
                            val cameraProvider = cameraProviderFuture.get()
                            val preview = Preview.Builder().build().also {
                                it.surfaceProvider = surfaceProvider
                            }
                            previewUseCase = preview

                            val imageAnalysis = ImageAnalysis.Builder()
                                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                .build()
                                .also {
                                    it.setAnalyzer(
                                        ContextCompat.getMainExecutor(ctx),
                                        ImageAnalysis.Analyzer { imageProxy ->
                                            processImage(imageProxy)
                                        }
                                    )
                                }

                            try {
                                cameraProvider.unbindAll()
                                cameraProvider.bindToLifecycle(
                                    lifecycleOwner,
                                    CameraSelector.DEFAULT_BACK_CAMERA,
                                    preview,
                                    imageAnalysis
                                )
                            } catch (e: Exception) {
                                Log.e("CameraX", "绑定摄像头失败", e)
                            }
                        }, ContextCompat.getMainExecutor(ctx))
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    } else {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "需要相机权限,请允许")
            Button(
                onClick = {
                    cameraPermissionState.launchPermissionRequest()
                }
            ) {
                Text(text = "允许权限")
            }
        }
    }
}


@androidx.annotation.OptIn(ExperimentalGetImage::class)
private fun processImage(imageProxy: ImageProxy) {
    val mediaImage = imageProxy.image ?: return
    val image = InputImage.fromMediaImage(
        mediaImage,
        imageProxy.imageInfo.rotationDegrees
    )

    // 配置二维码扫描器（支持多码识别）
    val scanner = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
        .build()

    val barcodeScanner = BarcodeScanning.getClient(scanner)
    barcodeScanner.process(image)
        .addOnSuccessListener { barcodes ->
            // 处理检测到的多个二维码
            if (barcodes.isNotEmpty()) {
                for (barcode in barcodes) {
                    val value = barcode.rawValue ?: "Unknown"
                    Log.d("QR_CODE", "Detected QR Code: $value")
                    // 更新UI显示结果（例如使用ViewModel）
                }
            }
        }
        .addOnCompleteListener {
            imageProxy.close() // 必须关闭ImageProxy
        }
}