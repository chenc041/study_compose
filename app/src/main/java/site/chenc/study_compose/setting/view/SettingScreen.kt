package site.chenc.study_compose.setting.view

import android.Manifest
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import site.chenc.study_compose.AppRoutes
import site.chenc.study_compose.R
import site.chenc.study_compose.setting.viewmodel.SettingViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    settingViewModel: SettingViewModel = hiltViewModel<SettingViewModel>()
) {
    val permissionNotificationState = rememberPermissionState(
        permission = Manifest.permission.POST_NOTIFICATIONS
    )
    val image = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        Log.d("TAG", "onActivityResult: $it")
    }
    var text by remember { mutableStateOf("") }
    val title = stringResource(R.string.test_notification_title  )
    val content = stringResource(R.string.test_notification_content)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
    ) {
        Button(onClick = {
            if (permissionNotificationState.status.isGranted) {
                settingViewModel.senNotification(title, content)
            } else {
                permissionNotificationState.launchPermissionRequest()
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(R.string.notification))
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(R.string.location))
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            navController.navigate(AppRoutes.WEBVIEW)
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(R.string.callLog))
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            image.launch("image/*")
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(R.string.phone_number))
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            settingViewModel.setStringValue("chen", "cheng")
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(R.string.set_string_value))
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            text = settingViewModel.getStringValue("chen")
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(R.string.get_string_value) + ": $text")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            settingViewModel.removeKey("chen")
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(R.string.delete_preference_key))
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            settingViewModel.openSettings()
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(R.string.open_settings))
        }

        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            navController.navigate(AppRoutes.SETTINGS_DETAIL)
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(R.string.nav_to_detail))
        }


        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            navController.navigate(AppRoutes.CAMERA)
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "相机页面")
        }

    }
}