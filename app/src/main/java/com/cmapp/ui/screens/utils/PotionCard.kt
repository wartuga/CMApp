package com.cmapp.ui.screens.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cmapp.R

@Composable
fun PotionCard(potionName: String, button: @Composable BoxScope.() -> Unit, modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.spell_bg),
            contentDescription = "Potion background",
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Text(
            text = potionName,
            fontSize = 24.sp,
            modifier = modifier
                .align(Alignment.TopCenter)
                .padding(top = 40.dp)
        )
        Text(
            text = "Potion Description",
            modifier = modifier
                .align(Alignment.Center)
        )
        button()
    }
}