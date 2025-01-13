package site.chenc.study_compose

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import site.chenc.study_compose.layout.LayoutScreen
import site.chenc.study_compose.ui.theme.Study_composeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Study_composeTheme {
                LayoutScreen()
            }
        }
    }
}
