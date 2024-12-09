package com.cmapp.ui

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
import com.cmapp.R

@Composable
fun NavigationBar(modifier: Modifier){
    Row(
        modifier = modifier.fillMaxWidth().background(Color.Gray),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        mapOf(
            "wand" to R.drawable.wand_icon,
            "potions" to R.drawable.potion_icon,
            "social" to R.drawable.social_icon,
            "profile" to R.drawable.profile_icon
        ).forEach { (str, img) ->
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                )) {
                Image(
                    painter = painterResource(img),
                    contentDescription = str,
                    modifier = modifier.padding(8.dp).size(35.dp)
                )
            }
        }
    }
}