package com.example.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.example.android.navigation.Navigation
import com.google.accompanist.insets.ProvideWindowInsets
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Navigation(window = window)
            }
        }
    }
}
