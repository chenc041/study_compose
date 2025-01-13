package site.chenc.study_compose.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import site.chenc.study_compose.R
import site.chenc.study_compose.home.ui.HomeScreen
import site.chenc.study_compose.search.ui.SearchScreen
import site.chenc.study_compose.search.viewmodel.UserViewModel
import site.chenc.study_compose.setting.ui.SettingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Layout() {
    val navController = rememberNavController()
    // 获取当前路由
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // 定义导航项和对应的内容
    val navigationItems = listOf(
        NavigationItem(
            title = stringResource(R.string.home),
            icon = Icons.Default.Home,
            route = "home",
        ),
        NavigationItem(
            title = stringResource(R.string.search),
            icon = Icons.Default.Search,
            route = "search",
        ),
        NavigationItem(
            title = stringResource(R.string.settings),
            icon = Icons.Default.Settings,
            route = "setting",
        )
    )

    Scaffold(
        // 顶部栏
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_name)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue,
                    titleContentColor = Color.White
                )
            )
        },
        // 底部导航栏
        bottomBar = {
            NavigationBar {
                navigationItems.forEachIndexed { _, item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = currentRoute == item.route,
                        onClick = {
                            if (currentRoute != item.route) {
                                navController.navigate(item.route) {
                                    popUpTo(currentRoute ?: "") {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        },
        // 内容区域
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                AppNavigation(navController = navController)
            }
        }
    )
}

// 定义导航项的数据类
data class NavigationItem(
    val title: String, // 导航项标题
    val icon: ImageVector, // 导航项图标
    val route: String,
)

@Composable
fun AppNavigation(navController: NavHostController) {  // 修改为NavHostController
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen()
        }
        composable("search") {
            SearchScreen()
        }
        composable("setting") {
            SettingsScreen()
        }
    }
}