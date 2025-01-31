package site.chenc.study_compose.home.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskItem(index: Int, onTaskCompleted: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), // 设置阴影
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface) // 设置背景色
    ) {
        Column(
            modifier = Modifier.padding(10.dp) // 为卡片内容添加内边距
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "十小时前", modifier = Modifier.padding(end = 8.dp)) // 第一个靠左的元素
                Text(text = "未接通") // 第二个靠左的元素
                Spacer(modifier = Modifier.weight(1f)) // 动态分配空间
                Text(text = "15006215830") // 靠右的元素
            }
            Text(
                text = "姓名姓名姓名姓名姓名姓名姓",
                modifier = Modifier.padding(bottom = 8.dp) // 为姓名文本添加底部内边距
            )
            Row(
                modifier = Modifier.padding(bottom = 8.dp) // 为第二个 Row 添加底部内边距
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "1",
                    tint = MaterialTheme.colorScheme.onSurface // 使用主题颜色
                )
                Text(text = "123", modifier = Modifier.padding(start = 8.dp)) // 为数字文本添加左边距
            }
            Button(
                onClick = {
                    onTaskCompleted(index);
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "完成任务")
            }
        }
    }
}