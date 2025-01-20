package com.cmapp.ui.screens.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.navigation.NavHostController
import com.cmapp.R
import com.cmapp.ui.screens.utils.ScreenSkeleton
import com.cmapp.ui.screens.utils.SwapImage
import com.cmapp.ui.screens.utils.UserCard

@Composable
fun WandSelectionScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController?
) {
    ScreenSkeleton(
        navController = navController,
        composable = {
            WandSelectionScreenContent(modifier)
        },
        modifier = modifier
    )
}

@Composable
private fun WandSelectionScreenContent(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = modifier.height(24.dp))
        UserCard(
            composable = {
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
                        painter = painterResource(id = R.drawable.pencil),
                        contentDescription = "Edit Profile",
                        modifier = modifier.size(20.dp),
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
                        painter = painterResource(id = R.drawable.gear_wheel),
                        contentDescription = "Settings",
                        modifier = modifier.size(20.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
            },
            modifier = modifier,
            //ALTERAR
            picture = R.drawable.face,
            wand = R.drawable.wand_side,
            username = "Harry Potter"
        )
        Row(modifier = modifier.padding(top=46.dp)) {
            Text(
                text = "PICK YOUR WAND",
                style = TextStyle(
                    fontSize = 36.sp,
                    fontFamily = FontFamily(Font(resId = R.font.harry)),
                    color = Color.White
                ),
                color = Color.White,
            )
        }
        Spacer(modifier = modifier.height(16.dp))
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SwapImage({
                Image(
                    painter = painterResource(id = R.drawable.wand), // Replace with your image resource
                    contentDescription = "Image at Bottom Center",
                    modifier = Modifier
                        .height(350.dp),
                    contentScale = ContentScale.FillHeight // Ensures the aspect ratio is maintained
                )
            }, modifier = modifier)
        }
        Row(
            modifier = modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = {},
                modifier = Modifier
                    .padding(top=16.dp),
                border = BorderStroke(2.dp, Color.White),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(83, 12, 114), // Background color
                    contentColor = Color.White   // Text/icon color
                ),
            ) {
                Text(text = "Select",
                    style = TextStyle(
                        fontSize = 34.sp,
                        fontFamily = FontFamily(Font(resId = R.font.harry)),
                        color = Color.White
                ),)
            }
        }
    }
}

@Preview
@Composable
fun WandSelectionScreenPreview() {
    WandSelectionScreen(modifier = Modifier, null)
}