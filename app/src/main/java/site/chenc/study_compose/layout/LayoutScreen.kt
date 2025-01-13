package site.chenc.study_compose.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import site.chenc.study_compose.R
import site.chenc.study_compose.home.view.HomeScreen
import site.chenc.study_compose.search.view.SearchScreen
import site.chenc.study_compose.setting.view.SettingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutScreen() {
    // 当前选中的路由
    var currentRoute by remember { mutableStateOf("home") }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = (currentRoute)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue,
                    titleContentColor = Color.White
                )
            )
        },
        // 底部导航栏
        bottomBar = {
            NavigationBar {
                NavigationConfig.navigationItems.forEach { item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = stringResource(item.title)
                            )
                        },
                        label = { Text(text = stringResource(item.title)) },
                        selected = currentRoute == item.route,
                        onClick = {
                            currentRoute = item.route // 切换路由
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
                // 根据当前路由显示对应的页面
                when (currentRoute) {
                    "home" -> HomeScreen()
                    "search" -> SearchScreen()
                    "setting" -> SettingsScreen()
                }
            }
        }
    )
}
