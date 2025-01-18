package com.cmapp.ui.screens.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cmapp.R

@Composable
fun PotionSpellCard(name: String, description:String, image:Int, buttonLabel: String, onButtonClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.card_bg),
            contentDescription = "Potion background",
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

        Image(
            painter = painterResource(id = image), // Replace with your image resource
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.height(260.dp)
            .fillMaxWidth()// The image will take up the full size of the Box
        )

        Column(
            modifier = Modifier
                .height(220.dp).padding(top = 14.dp),
            verticalArrangement = Arrangement.SpaceBetween, // Distributes elements with space between them
            horizontalAlignment = Alignment.CenterHorizontally // Centers elements horizontally
        ) {
            Text(
                text = name,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 32.dp),
                style = TextStyle(
                    fontSize = 32.sp,
                    fontFamily = FontFamily(Font(resId = R.font.harry)),
                    color = Color.Black
                ),
            )

            Text(
                text = description,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 32.dp),
                style = TextStyle(
                    fontSize = 30.sp,
                    fontFamily = FontFamily(Font(resId = R.font.harry)),
                    color = Color.Black
                )
            )
            Button(
                onClick = onButtonClick,
                border = BorderStroke(2.dp, Color.White),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(83, 12, 114), // Background color
                    contentColor = Color.White   // Text/icon color
                ),
            ) {
                Text(
                    text = buttonLabel, color = Color.White, style = TextStyle(
                        fontSize = 34.sp,
                        fontFamily = FontFamily(Font(resId = R.font.harry)),
                        color = Color.White
                    )
                )
            }
        }
    }
}

@Composable
@Preview
fun PotionSpellCardPreview(){
    PotionSpellCard(
        name = "REPARIFORS",
        description = "Heals magical ailments like poisoning or paralysis",
        image = R.drawable.potion,
        onButtonClick = {},
        buttonLabel = "Practice"
    )
}