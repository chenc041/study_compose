package site.chenc.study_compose

import android.graphics.Color
import android.os.Build
import android.view.Window
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import site.chenc.study_compose.layout.LayoutScreen
import site.chenc.study_compose.setting.view.QRCodeScannerScreen
import site.chenc.study_compose.setting.view.SettingsDetailScreen
import site.chenc.study_compose.splash.view.SplashScreen
import site.chenc.study_compose.ui.common.SnackbarManagerViewModel
import site.chenc.study_compose.ui.theme.Study_composeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        window.isNavigationBarContrastEnforced = false

        setContent {
            Study_composeTheme {
                Scaffold(
                    snackbarHost = {
                        CustomSnackbarHost()
                    },
                ) { paddingValues -> RootApp(paddingValues) }
            }
        }
    }
}

@Composable
fun CustomSnackbarHost(
    snackbarManagerViewModel: SnackbarManagerViewModel = hiltViewModel<SnackbarManagerViewModel>()
) {
    Box(modifier = Modifier.fillMaxSize()) {
        SnackbarHost(
            hostState = snackbarManagerViewModel.hostState,
            modifier = Modifier
                .align(Alignment.TopCenter) // 在 Box 内使用 align
                .padding(top = 100.dp)
        ) { snackbarData ->
            Snackbar(snackbarData = snackbarData)
        }
    }
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun RootApp(paddingValues: PaddingValues) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppRoutes.SPLASH,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 0.dp, bottom = paddingValues.calculateBottomPadding()),

        enterTransition = {
            slideIntoContainer(
                towards = SlideDirection.Left,
                animationSpec = tween(300, easing = LinearOutSlowInEasing)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = SlideDirection.Left,
                animationSpec = tween(300, easing = FastOutLinearInEasing)
            )
        },
        // B → A 返回动画（反向）
        popEnterTransition = {
            slideIntoContainer(
                towards = SlideDirection.Right,
                animationSpec = tween(300, easing = LinearOutSlowInEasing)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = SlideDirection.Right,
                animationSpec = tween(300, easing = FastOutLinearInEasing)
            )
        }
    ) {
        composable(
            route = AppRoutes.SPLASH
        ) {
            SplashScreen(navController)
        }
        composable(
            route = AppRoutes.ROOT
        ) {
            LayoutScreen(navController)
        }

        composable(
            route = AppRoutes.SETTINGS_DETAIL,
        ) {
            SettingsDetailScreen(navController)
        }

        composable(
            route = AppRoutes.CAMERA
        ) {
            QRCodeScannerScreen(navController)
        }
    }
}