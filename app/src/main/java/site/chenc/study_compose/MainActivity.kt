package site.chenc.study_compose

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.isNavigationBarContrastEnforced = false
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            Study_composeTheme {
                Scaffold(
                    snackbarHost = { CustomSnackbarHost() },
                    content = { scaffoldPadding  -> RootApp(scaffoldPadding ) }
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun RootApp(scaffoldPadding : PaddingValues) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppRoutes.SPLASH,
        modifier = Modifier
            .fillMaxSize(),
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

@Composable
fun CustomSnackbarHost(
    snackbarManagerViewModel: SnackbarManagerViewModel = hiltViewModel<SnackbarManagerViewModel>()
) {
    SnackbarHost(
        hostState = snackbarManagerViewModel.hostState,
    ) { snackbarData ->
        Snackbar(
            snackbarData = snackbarData,
            contentColor = Color.Black,
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    }
}
