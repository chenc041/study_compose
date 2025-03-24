package site.chenc.study_compose.home.view

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import site.chenc.study_compose.home.view.components.TaskItem
import site.chenc.study_compose.models.SnackbarMessage
import site.chenc.study_compose.ui.common.SnackbarManagerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    snackbarManagerViewModel: SnackbarManagerViewModel = hiltViewModel<SnackbarManagerViewModel>()
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(10) { index ->
            TaskItem(
                index = index,
                onTaskCompleted = {
                    snackbarManagerViewModel.showSnackbar(
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
