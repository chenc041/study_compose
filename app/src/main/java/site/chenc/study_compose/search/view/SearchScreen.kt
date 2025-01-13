package site.chenc.study_compose.search.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import site.chenc.study_compose.R
import site.chenc.study_compose.search.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(viewModel: UserViewModel = hiltViewModel<UserViewModel>()) {
    // 监听 ViewModel 的状态
    val user by viewModel.user.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    // 在 LaunchedEffect 中触发网络请求
    LaunchedEffect(Unit) {
        viewModel.fetchUser("chenc041")
    }

    Scaffold(
        content = { innerPadding ->
            // 根据状态显示 UI
            Column(modifier = Modifier.padding(innerPadding)) {
                if (isLoading) {
                    CircularProgressIndicator()
                } else if (error != null) {
                    Text(text = "Error: ${error!!}", color = MaterialTheme.colorScheme.error)
                } else {
                    user?.let {
                        Text(text = "ID: ${it.id}")
                        Text(text = "Name: ${it.name}")
                        Text(text = "Email: ${it.login}")
                    }
                }
            }
        }
    )
}