package site.chenc.study_compose.setting.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen() {
    Text(
        text = "欢迎来到设置页面！",
        fontSize = 24.sp,
        modifier = Modifier.padding(16.dp)
    )
}