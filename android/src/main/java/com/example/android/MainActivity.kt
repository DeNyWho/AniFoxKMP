package com.example.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.android.composable.ImageLoaderSingleton
import com.example.android.navigation.Navigation
import com.example.android.ui.AniFoxTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImageLoaderSingleton.initialize(applicationContext)
        setContent {
            AniFoxTheme {
                Navigation(window = window)
            }
        }
    }
}
