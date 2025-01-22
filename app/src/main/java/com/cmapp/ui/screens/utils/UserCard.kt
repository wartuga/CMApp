package com.cmapp.ui.screens.utils

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.cmapp.R

@Composable
fun UserCard(
    username: String,
    wand: String,
    picture: String,
    composable: @Composable () -> Unit,
    modifier: Modifier
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .height(70.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile_bg),
            contentDescription = "profile background",
            modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(24.dp)),
            contentScale = ContentScale.Crop
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberAsyncImagePainter(Uri.parse(picture)),
                contentDescription = "Profile picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(70.dp)
                    .padding(8.dp)
                    .clip(CircleShape) // Crop the image into a circle
                    .border(2.dp, Color.Black, CircleShape)
            // Apply a black border with a 2dp width
            )
            Column(modifier = modifier.padding(top = 8.dp, bottom = 8.dp)) {
                Text(text = username, style = TextStyle(
                    fontSize = 30.sp,
                    fontFamily = FontFamily(Font(resId = R.font.harry)),
                    color = Color.Black
                )
                )
                Image(
                    painter = rememberAsyncImagePainter(Uri.parse(wand)), // Replace with your image resource
                    contentDescription = "Image at Bottom Center",
                    modifier = Modifier
                        .width(140.dp),
                    contentScale = ContentScale.Fit // Ensures the aspect ratio is maintained
                )
            }
            composable()
        }
    }
}

@Preview
@Composable
fun UserCardPreview() {
    UserCard(
        modifier = Modifier, username = "Harry Potter", picture = "", wand = "", composable = {
        Button(
            onClick = {},
            modifier = Modifier
                .size(50.dp)
                .padding(6.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(83, 12, 114), // Background color
                contentColor = Color.White   // Text/icon color
            ),
            border = BorderStroke(2.dp, Color.White),
            contentPadding = PaddingValues(0.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.edit),
                contentDescription = "Edit Profile",
                modifier = Modifier.size(20.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
        Button(
            onClick = {},
            modifier = Modifier
                .size(50.dp)
                .padding(6.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(83, 12, 114), // Background color
                contentColor = Color.White   // Text/icon color
            ),
            border = BorderStroke(2.dp, Color.White),
            contentPadding = PaddingValues(0.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.exit),
                contentDescription = "Settings",
                modifier = Modifier.size(20.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    },)
}