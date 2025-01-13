package site.chenc.study_compose.layout

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import site.chenc.study_compose.R


// 定义导航项的数据类
data class NavigationItem(
    val title: Int, // 导航项标题
    val icon: ImageVector, // 导航项图标
    val route: String, // 路由
)

object NavigationConfig {
    val navigationItems = listOf(
        NavigationItem(
            title = R.string.home,
            icon = Icons.Default.Home,
            route = "home",
        ),
        NavigationItem(
            title = R.string.search,
            icon = Icons.Default.Search,
            route = "search",
        ),
        NavigationItem(
            title = R.string.settings,
            icon = Icons.Default.Settings,
            route = "setting",
        )
    )
}