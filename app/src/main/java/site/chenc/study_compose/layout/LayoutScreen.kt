package site.chenc.study_compose.layout

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import site.chenc.study_compose.home.view.HomeScreen
import site.chenc.study_compose.search.view.SearchScreen
import site.chenc.study_compose.setting.view.SettingsScreen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutScreen(navController: NavController) {
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    Column(modifier = Modifier.fillMaxSize()) {
        Surface(modifier = Modifier.weight(1f)) {
            when (selectedIndex) {
                0 -> HomeScreen(navController)
                1 -> SearchScreen(navController)
                2 -> SettingsScreen(navController)
            }
        }
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
    }
}
