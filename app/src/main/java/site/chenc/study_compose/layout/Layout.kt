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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import site.chenc.study_compose.R // 确保正确导入 R 类

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Layout() {
    var selectedPage by remember { mutableIntStateOf(0) }

    // 定义导航项和对应的内容
    val navigationItems = listOf(
        NavigationItem(
            title = stringResource(R.string.home),
            icon = Icons.Default.Home,
            content = { Text("首页内容", fontSize = 24.sp, textAlign = TextAlign.Center) }
        ),
        NavigationItem(
            title = stringResource(R.string.search),
            icon = Icons.Default.Search,
            content = { Text("搜索内容", fontSize = 24.sp, textAlign = TextAlign.Center) }
        ),
        NavigationItem(
            title = stringResource(R.string.settings),
            icon = Icons.Default.Settings,
            content = { Text("设置内容", fontSize = 24.sp, textAlign = TextAlign.Center) }
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
                navigationItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = selectedPage == index,
                        onClick = { selectedPage = index }
                    )
                }
            }
        },
        // 内容区域
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                navigationItems[selectedPage].content()
            }
        }
    )
}

// 定义导航项的数据类
data class NavigationItem(
    val title: String, // 导航项标题
    val icon: ImageVector, // 导航项图标
    val content: @Composable () -> Unit // 导航项对应的内容
)