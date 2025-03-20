package site.chenc.study_compose.layout

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import site.chenc.study_compose.AppRoutes
import site.chenc.study_compose.R
import site.chenc.study_compose.home.view.HomeScreen
import site.chenc.study_compose.search.view.SearchScreen
import site.chenc.study_compose.setting.view.QRCodeScannerScreen
import site.chenc.study_compose.setting.view.SettingsDetailScreen
import site.chenc.study_compose.setting.view.SettingsScreen
import site.chenc.study_compose.splash.view.SplashScreen
import site.chenc.study_compose.ui.common.SnackbarManagerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun LayoutScreen() {
    val navController = rememberNavController()
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Log.d("currentRoute", "currentRoute $currentRoute")

    Scaffold(
        topBar = {
            when(selectedIndex) {
                0 ->
                    CenterAlignedTopAppBar(
                        title = { Text(stringResource(id = R.string.home)) },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                        ),
                    )
                1 ->
                    CenterAlignedTopAppBar(
                        title = { Text(stringResource(id = R.string.search)) },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                        ),
                    )
                2 ->
                    CenterAlignedTopAppBar(
                        title = { Text(stringResource(id = R.string.settings)) },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                        ),
                    )
                else -> null
            }
        },
        snackbarHost = { CustomSnackbarHost() },
        content = { scaffoldPadding ->
            NavHost(
                navController = navController,
                startDestination = AppRoutes.SPLASH,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding),
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
                    when (selectedIndex) {
                        0 -> HomeScreen(navController)
                        1 -> SearchScreen(navController)
                        2 -> SettingsScreen(navController)
                    }
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
        },
        bottomBar = {
            when (currentRoute) {
                AppRoutes.ROOT ->
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,) {
                        NavigationConfig.navigationItems.forEachIndexed { index, item ->
                            NavigationBarItem(
                                icon = {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = stringResource(item.title)
                                    )
                                },
                                label = { Text(text = stringResource(item.title)) },
                                selected = selectedIndex == index,
                                onClick = {
                                    selectedIndex = index // 更新选中的下标
                                }
                            )
                        }
                    }
                else -> null
            }
        }
    )
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
