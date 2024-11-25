package com.cmapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.cmapp.navigation.NavGraph
import com.cmapp.ui.theme.CMAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CMAppTheme {
                val navController = rememberNavController()
                NavGraph (
                    navController
                )
            }
        }
    }
}