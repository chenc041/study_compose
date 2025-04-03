package site.chenc.study_compose.root

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import site.chenc.study_compose.AppRoutes
import site.chenc.study_compose.R
import site.chenc.study_compose.home.view.HomeScreen
import site.chenc.study_compose.search.view.SearchScreen
import site.chenc.study_compose.setting.view.SettingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun RootScreen(navController: NavController) {
    var selectedRoute by rememberSaveable { mutableStateOf(AppRoutes.HOME) }
    val routeMap = mapOf(
        0 to AppRoutes.HOME,
        1 to AppRoutes.SEARCH,
        2 to AppRoutes.SETTINGS,
    )
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(R.string.app_name)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        content = { paddingValue ->
            Box(
                modifier = Modifier
                    .padding(paddingValue)
                    .fillMaxSize()
            ) {
                when (selectedRoute) {
                    AppRoutes.HOME -> HomeScreen(navController)
                    AppRoutes.SEARCH -> SearchScreen(navController)
                    AppRoutes.SETTINGS -> SettingsScreen(navController)
                }
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                NavigationConfig.navigationItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = stringResource(item.title)
                            )
                        },
                        label = { Text(text = stringResource(item.title)) },
                        selected = selectedRoute == routeMap[index],
                        onClick = {
                            selectedRoute = routeMap[index] ?: AppRoutes.HOME
                        }
                    )
                }
            }
        }
    )
}
