package site.chenc.study_compose

import android.os.Build
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import site.chenc.study_compose.root.RootScreen
import site.chenc.study_compose.setting.view.QRCodeScannerScreen
import site.chenc.study_compose.setting.view.SettingsDetailScreen
import site.chenc.study_compose.ui.common.SnackbarManagerViewModel
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


/**
 * 基础屏幕, 主要目的是配置页面路由导航
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun BaseScreen() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.ROOT,
        modifier = Modifier
            .fillMaxSize(),
        enterTransition = {
            slideIntoContainer(
                towards = SlideDirection.Left,
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = SlideDirection.Left,
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = SlideDirection.Right,
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = SlideDirection.Right,
            )
        }
    ) {
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
    CustomSnackbarHost()
}


/**
 * 自定义SnackbarHost
 */
@Composable
fun CustomSnackbarHost(
    snackbarManagerViewModel: SnackbarManagerViewModel = hiltViewModel<SnackbarManagerViewModel>()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(Unit) {
        snackbarManagerViewModel.snackbarMessage.collect {
            val result = snackbarHostState.showSnackbar(
                message = it.message,
                actionLabel = it.actionLabel,
                withDismissAction = it.withDismissAction,
            )
            Log.d("2222", result.toString())
            when(result) {
                SnackbarResult.ActionPerformed -> {
                    it.onAction?.invoke()
                }
                SnackbarResult.Dismissed -> {
                    it.onDismiss?.invoke()
                }
            }
        }
    }

    Box(modifier = Modifier.padding(top = 30.dp).fillMaxSize()) {
        SnackbarHost(
            hostState = snackbarHostState,
        ) { snackbarData ->
            Snackbar(
                snackbarData = snackbarData,
                contentColor = Color.Black,
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        }
    }
}