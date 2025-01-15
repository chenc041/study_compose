package site.chenc.study_compose.layout

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import site.chenc.study_compose.home.view.HomeScreen
import site.chenc.study_compose.search.view.SearchScreen
import site.chenc.study_compose.setting.view.SettingsScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutScreen() {
    val navController = rememberNavController()
    var currentRoute by remember { mutableStateOf("home") }
    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
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
                            navController.navigate(item.route) {
                                currentRoute = item.route
                            }
                        }
                    )
                }
            }
        },
    ){
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                HomeScreen()
            }
            composable("setting") {
                SettingsScreen()
            }
            composable("search") {
                SearchScreen()
            }
        }
    }
}
