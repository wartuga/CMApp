package com.cmapp

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.compose.rememberNavController
import com.cmapp.model.data.DataBaseHelper.addLearnedPotion
import com.cmapp.model.data.DataBaseHelper.addLearnedSpell
import com.cmapp.model.data.StorageHelper.getUsername
import com.cmapp.model.data.StorageHelper.setUsername
import com.cmapp.navigation.NavGraph
import com.cmapp.ui.theme.CMAppTheme
import com.cmapp.ui.theme.DarkColorScheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

val customFontFamily = FontFamily(
    Font(resId = R.font.harry) // Replace with your font file name
)


@Composable
fun MyAppTheme(content: @Composable () -> Unit) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(Color.Black)

    MaterialTheme(
        typography = MaterialTheme.typography.copy(
            bodyLarge = TextStyle(
                fontFamily = customFontFamily,
            ),
            titleLarge = TextStyle(
                fontFamily = customFontFamily,
            )
        ),
        content = content,
        colorScheme = DarkColorScheme
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUsername(this, "Test") //TESTE

        //FRIEND TEST
        //addLearnedPotion("FriendTest", "-OGyy3IfmE71PjebDJVE")
        //addLearnedSpell("FriendTest", "-OGvCKrwmDtN0fp9S59J")

        enableEdgeToEdge()
        setContent {
            MyAppTheme {
                var navController = rememberNavController()
                NavGraph (
                    navController,
                    this
                )
            }
        }
    }
}