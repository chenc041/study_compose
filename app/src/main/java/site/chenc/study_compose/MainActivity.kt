package site.chenc.study_compose

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import site.chenc.study_compose.layout.LayoutScreen
import site.chenc.study_compose.splash.view.SplashScreen
import site.chenc.study_compose.ui.common.SnackbarManagerViewModel
import site.chenc.study_compose.ui.theme.Study_composeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
            .padding(top = 0.dp, bottom = paddingValues.calculateBottomPadding())
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
    }
}