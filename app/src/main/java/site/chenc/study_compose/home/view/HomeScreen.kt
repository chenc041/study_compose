package site.chenc.study_compose.home.view

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import site.chenc.study_compose.home.view.components.TaskItem
import site.chenc.study_compose.home.viewmodel.HomeViewModel
import site.chenc.study_compose.models.SnackbarMessage

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel<HomeViewModel>()
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(10) { index ->
            TaskItem(
                index = index,
                onTaskCompleted = {
                    homeViewModel.showSnackBar(
                        SnackbarMessage(
                            message = "这是一个Snackbar",
                            actionLabel = "确认",
                            onAction = {
                                Log.d("管理", "测试")
                            }
                        )
                    )
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
