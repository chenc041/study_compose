package site.chenc.study_compose.home.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue,
                    titleContentColor = Color.Black
                ),
            )
        },
        content = { innerPadding ->
            // 根据状态显示 UI
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                items(100) { index ->
                    Card1(index)
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    )
}


@Composable
fun Card1(index: Int) {
    Card(
        modifier = Modifier
            .height(100.dp)
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
    ) {
        Row {
            Text(text = "Hello")
            Text(text = "Hello$index")
        }

    }
}