package com.example.mywhether.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mywhether.presentation.screens.detail.DetailScreen
import com.example.mywhether.presentation.screens.home.HomeScreen
import com.example.mywhether.ui.theme.MyWhetherTheme
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyWhetherTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                mutableListOf(Color.Red, Color.Blue)
                            )
                        ),
                    color = Color.Transparent
                ) {
                    NavGraph()
                }
            }
        }
    }

    @Composable
    private fun NavGraph() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                HomeScreen(modifier = Modifier.fillMaxSize(), navController = navController)
            }
            composable(
                "detail/{city}",
                arguments = listOf(navArgument("city") {
                    type = NavType.StringType
                })
            ) {
                DetailScreen(modifier = Modifier.fillMaxSize(), it.arguments?.getString("city"), navController)
            }
        }
    }
}
