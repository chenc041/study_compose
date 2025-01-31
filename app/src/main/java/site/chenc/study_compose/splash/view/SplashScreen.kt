package site.chenc.study_compose.splash.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import site.chenc.study_compose.AppRoutes
import site.chenc.study_compose.BuildConfig

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashScreen(navController: NavController) {
    val baseUrl = BuildConfig.BASE_URL;
    LaunchedEffect(Unit) {
        delay(300)
        navController.navigate(AppRoutes.ROOT) {
            popUpTo(AppRoutes.SPLASH) {
                inclusive = true
            }
        }
    }

    Scaffold(
        content = { innerPadding ->
            // 根据状态显示 UI
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Text(text = "这是启动页 baseUrl $baseUrl")
            }
        }
    )
}