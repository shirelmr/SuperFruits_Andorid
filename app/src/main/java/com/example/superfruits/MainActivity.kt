package com.example.superfruits

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.superfruits.ui.screens.FoodSelection
import com.example.superfruits.ui.screens.HomeScreen
import com.example.superfruits.ui.screens.SplashScreen
import com.example.superfruits.ui.theme.SuperFruitsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SuperFruitsTheme {
                var currentScreen by remember { mutableStateOf("splash") }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    when (currentScreen) {
                        "splash" -> SplashScreen(
                            modifier = Modifier.padding(innerPadding),
                            onNavigateToHome = { currentScreen = "home" }
                        )
                        "home" -> HomeScreen(
                            modifier = Modifier.padding(innerPadding),
                            onNavigateToHelp = { currentScreen = "help"}
                        )
                        "help" -> FoodSelection(
                            modifier = Modifier.padding(innerPadding),
                            onNavigateToHome = { currentScreen = "home"}
                        )
                    }
                }
            }
        }
    }
}