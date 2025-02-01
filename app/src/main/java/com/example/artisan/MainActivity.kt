package com.example.artisan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import com.example.artisan.ui.theme.ArtisanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                0, 0
            ),
            navigationBarStyle = SystemBarStyle.light(
                0, 0
            )
        )
        setContent {

            WindowCompat.setDecorFitsSystemWindows(window, false)

            ArtisanTheme {
                NavGraph()
            }
        }
    }
}