package com.cmapp.ui.screens.utils

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.cmapp.R
import com.cmapp.model.data.DataBaseHelper
import com.cmapp.model.data.getWandsFront
import com.cmapp.model.data.getWandsSide

@Composable
fun SwapWand(username: String, initialWand: String, modifier: Modifier) {

    val wandsFront = getWandsFront()
    val wandsSide = getWandsSide()

    var currentIdx by remember { mutableIntStateOf(wandsFront.indexOf(initialWand)) }
    var wandImage by remember { mutableStateOf(wandsFront[currentIdx]) }

    if (currentIdx != -1) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.left_arrow),
                contentDescription = "Left Arrow",
                modifier = Modifier.clickable {
                    if (currentIdx == 0) {
                        currentIdx = wandsFront.size - 1
                    } else {
                        currentIdx -= 1
                    }
                    wandImage = wandsFront[currentIdx]
                }
                    .size(50.dp)
                    .padding(8.dp),
            )
            Image(
                painter = rememberAsyncImagePainter(Uri.parse(wandImage)), // Replace with your image resource
                contentDescription = "Wand image",
                modifier = Modifier
                    .height(350.dp),
                contentScale = ContentScale.FillHeight // Ensures the aspect ratio is maintained
            )
            Image(
                painter = painterResource(id = R.drawable.right_arrow),
                contentDescription = "Left Arrow",
                modifier = Modifier.clickable {
                    if (currentIdx == wandsFront.size - 1) {
                        currentIdx = 0
                    } else {
                        currentIdx += 1
                    }
                    wandImage = wandsFront[currentIdx]
                }
                    .size(50.dp)
                    .padding(8.dp),
            )
        }
        Row(
            modifier = modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
        ) {
            if (wandsFront[currentIdx] == initialWand) {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .padding(top = 16.dp),
                    border = BorderStroke(2.dp, Color.White),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(126, 119, 130), // Background color
                        contentColor = Color.White   // Text/icon color
                    ),
                ) {
                    Text(
                        text = "Selected",
                        style = TextStyle(
                            fontSize = 34.sp,
                            fontFamily = FontFamily(Font(resId = R.font.harry)),
                            color = Color.White
                        ),
                    )
                }
            } else {
                Button(
                    onClick = {
                        DataBaseHelper.updateWand(
                            username,
                            wandsFront[currentIdx],
                            wandsSide[currentIdx]
                        )
                    },
                    modifier = Modifier
                        .padding(top = 16.dp),
                    border = BorderStroke(2.dp, Color.White),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(83, 12, 114), // Background color
                        contentColor = Color.White   // Text/icon color
                    ),
                ) {
                    Text(
                        text = "Select",
                        style = TextStyle(
                            fontSize = 34.sp,
                            fontFamily = FontFamily(Font(resId = R.font.harry)),
                            color = Color.White
                        ),
                    )
                }
            }
        }
    }
}