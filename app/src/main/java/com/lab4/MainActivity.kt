package com.lab4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.lab4.ui.HomePage
import com.lab4.ui.theme.CMAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CMAppTheme {
                //Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomePage(
                        //modifier = Modifier.padding(innerPadding)
                    )
                //}
            }
        }
    }
}