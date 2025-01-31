package site.chenc.study_compose.home.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import site.chenc.study_compose.R
import site.chenc.study_compose.home.view.components.TaskItem
import site.chenc.study_compose.ui.common.SnackbarManagerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    snackbarManagerViewModel: SnackbarManagerViewModel = hiltViewModel<SnackbarManagerViewModel>()
) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(id = R.string.home)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
            )
        },
        content = { innerPadding ->
            // 根据状态显示 UI
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                items(100) { index ->
                    TaskItem(
                        index = index,
                        onTaskCompleted = {
                            snackbarManagerViewModel.showSnackbar("任务完成")
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    )
}
