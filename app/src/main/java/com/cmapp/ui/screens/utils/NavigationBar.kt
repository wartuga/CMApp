package com.cmapp.ui.screens.utils

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cmapp.R
import com.cmapp.navigation.Screens

@Composable
fun NavigationBar(modifier: Modifier, navController: NavController?){

    val screenMap = mapOf(
        Screens.LearnSpells to R.drawable.wand_icon,
        Screens.LearnPotions to R.drawable.potion_icon,
        Screens.FriendsSocial to R.drawable.social_icon,
        Screens.WandProfile to R.drawable.profile_icon
    )

    Row(
        modifier = modifier.fillMaxWidth().background(Color.Black).padding(bottom = 38.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        screenMap.forEach { (screen, image) ->
            Button(
                onClick = {
                    navController!!.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(image),
                        contentDescription = screen.section,
                        modifier = modifier.size(30.dp)
                    )
                    if (navController!!.currentBackStackEntry?.destination?.route?.let { getCurrentSection(it) } ==
                        getCurrentSection(screen.route)) {
                        Text(
                            text = screen.section,
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(resId = R.font.harry)),
                                color = Color.White
                            )
                        )
                    }
                }

            }
        }
    }
}

fun getCurrentSection(route: String): String? {
    val regex = "_(.*?)_".toRegex()
    val matchResult = regex.find(route)
    return matchResult?.groups?.get(1)?.value // Get the first captured group
}
