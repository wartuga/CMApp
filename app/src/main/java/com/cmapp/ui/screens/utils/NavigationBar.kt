package com.cmapp.ui.screens.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cmapp.R
import com.cmapp.navigation.Screens

@Composable
fun NavigationBar(modifier: Modifier, navController: NavController?){
    Row(
        modifier = modifier.fillMaxWidth().background(Color.Black).padding(bottom = 32.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        mapOf(
            Screens.Spells.route to R.drawable.wand_icon,
            Screens.Potions.route to R.drawable.potion_icon,
            Screens.Social.route to R.drawable.social_icon,
            Screens.Profile.route to R.drawable.profile_icon
                ).forEach { (route, img) ->
                    Button(
                        onClick = {navController!!.navigate(route){
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.White
                        )) {
                            Image(
                                painter = painterResource(img),
                                contentDescription = "",
                                modifier = modifier.padding(6.dp).size(30.dp)
                            )
            }
        }
    }
}