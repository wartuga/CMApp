package com.cmapp.ui.screens.wand

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cmapp.R
import com.cmapp.ui.screens.utils.ScreenSkeleton
import com.cmapp.ui.screens.utils.SwapButton
import com.cmapp.ui.screens.utils.UserCard

@Composable
fun SelectionScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController?
) {
    ScreenSkeleton(
        navController = navController,
        composable = {
            SelectionScreenContent(modifier)
        },
        modifier = modifier
    )
}

@Composable
private fun SelectionScreenContent(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = modifier.height(16.dp))
        UserCard(
            username = "Harry Potter",
            composable = {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .size(40.dp)
                        .padding(6.dp),
                    border = BorderStroke(1.dp, Color.White),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.pencil),
                        contentDescription = "Edit Profile",
                        modifier = modifier.size(15.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
                Button(
                    onClick = {},
                    modifier = Modifier
                        .size(40.dp)
                        .padding(6.dp),
                    border = BorderStroke(1.dp, Color.White),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.gear_wheel),
                        contentDescription = "Settings",
                        modifier = modifier.size(15.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
            },
            modifier = modifier
        )
        Row(modifier = modifier.padding(8.dp)) {
            Text(
                text = "Pick your wand",
                color = Color.White,
                fontSize = 36.sp
            )
        }
        Spacer(modifier = modifier.height(8.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(400.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SwapButton(label = "Wand Image", modifier = modifier)
        }
        Row(
            modifier = modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Button(
                onClick = {},
                modifier = Modifier
                    .padding(30.dp),
                border = BorderStroke(1.dp, Color.White)
            ) {
                Text(text = "Select", color = Color.White, fontSize = 24.sp)
            }
        }
    }
}

@Preview
@Composable
fun SelectionScreenPreview() {
    SelectionScreen(modifier = Modifier, null)
}