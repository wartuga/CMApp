package com.cmapp.ui.screens.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cmapp.R

@Composable
fun RemoveButton(modifier: Modifier) {
    Button(
        onClick = {  },
        modifier = modifier
            .height(40.dp)
            .width(70.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
        border = BorderStroke(1.dp, Color.White),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = "Remove",
            color = Color.White,
            fontSize = 16.sp
        )
    }
}

@Composable
fun SwapButton(label: String, modifier: Modifier){
    Image(
        painter = painterResource(id = R.drawable.arrow_left),
        contentDescription = "Left Arrow",
        modifier = Modifier
            .size(50.dp)
            .padding(8.dp),
        colorFilter = ColorFilter.tint(Color.White)
    )
    Text(text = label, color = Color.White, fontSize = 24.sp)
    Image(
        painter = painterResource(id = R.drawable.arrow_right),
        contentDescription = "Left Arrow",
        modifier = Modifier
            .size(50.dp)
            .padding(8.dp),
        colorFilter = ColorFilter.tint(Color.White)
    )
}