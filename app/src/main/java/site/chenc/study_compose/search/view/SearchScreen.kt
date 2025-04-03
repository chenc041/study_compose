package site.chenc.study_compose.search.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import site.chenc.study_compose.models.UiState
import site.chenc.study_compose.search.viewmodel.SearchViewModel

@Composable
fun SearchScreen(navController: NavController ,viewModel: SearchViewModel = hiltViewModel<SearchViewModel>()) {
    // 监听 ViewModel 的状态
    val userState by viewModel.userState.collectAsState()
    val name by remember { mutableStateOf("chenc041") }

    // 在 LaunchedEffect 中触发网络请求
    LaunchedEffect(name) {
        viewModel.fetchUser(name)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        when(val user = userState) {
            is UiState.Success -> {
                Text(text = "ID: ${user.data.id}")
                Text(text = "Name: ${user.data.name}")
                Text(text = "Email: ${user.data.login}")
            }
            is UiState.Error -> {
                Text(text = "Error: ${user.message}")
            }
            else -> {
                Text(text = "Loading...")
            }
        }
    }
}