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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import site.chenc.study_compose.root.RootScreen
import site.chenc.study_compose.setting.view.QRCodeScannerScreen
import site.chenc.study_compose.setting.view.SettingsDetailScreen
import site.chenc.study_compose.splash.view.SplashScreen
import site.chenc.study_compose.ui.theme.Study_composeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.isNavigationBarContrastEnforced = false
        setContent {
            Study_composeTheme {
                BaseScreen()
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun BaseScreen() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppRoutes.SPLASH,
        modifier = Modifier
            .fillMaxSize(),
        enterTransition = {
            slideIntoContainer(
                towards = SlideDirection.Left,
                animationSpec = tween(360, easing = LinearOutSlowInEasing)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = SlideDirection.Left,
                animationSpec = tween(360, easing = FastOutLinearInEasing)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = SlideDirection.Right,
                animationSpec = tween(360, easing = LinearOutSlowInEasing)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = SlideDirection.Right,
                animationSpec = tween(360, easing = FastOutLinearInEasing)
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
            RootScreen(navController)
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
