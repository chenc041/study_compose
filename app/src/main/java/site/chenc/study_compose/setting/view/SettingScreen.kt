package site.chenc.study_compose.setting.view

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.delay
import site.chenc.study_compose.R
import site.chenc.study_compose.setting.viewmodel.SettingViewModel
import site.chenc.study_compose.utils.NotificationUtils
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun SettingsScreen(navController: NavController, settingViewModel: SettingViewModel = hiltViewModel<SettingViewModel>()) {

    val permissionNotificationState = rememberPermissionState(
        permission = Manifest.permission.POST_NOTIFICATIONS
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(id = R.string.settings)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = Color.Black
                ),
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .padding(innerPadding)
            ) {
                Button(onClick = {
                    if (permissionNotificationState.status.isGranted) {
                        settingViewModel.senNotification();
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
                Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                    Text(text = stringResource(R.string.callLog))
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                    Text(text = stringResource(R.string.phone_number))
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                    Text(text = stringResource(R.string.sms))
                }
            }

        }
    )
}