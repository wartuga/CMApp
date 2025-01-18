package com.cmapp.ui.screens.social

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cmapp.R
import com.cmapp.navigation.Screens
import com.cmapp.ui.screens.utils.RemoveButton
import com.cmapp.ui.screens.utils.ScreenSkeleton
import com.cmapp.ui.screens.utils.UserCard

@Composable
fun FriendsListScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController?,
    context: Context?
) {
    ScreenSkeleton(
        navController = navController,
        composable = { FriendsListScreenContent(modifier, navController) },
        modifier = modifier
    )
}

@Composable
private fun FriendsListScreenContent(modifier: Modifier, navController: NavHostController?) {

    val friendRequests = listOf("Friend 6", "Friend 7")
    val friends = listOf("Friend 1", "Friend 2", "Friend 3", "Friend 4", "Friend 5")

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier.fillMaxSize().verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Search bar
        Button(
            onClick = {  },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                alignment = Alignment.TopStart
            )
        }

        Spacer(modifier = modifier.height(16.dp))

        Text(
            text = "FRIEND REQUESTS",
            style = TextStyle(
                fontSize = 36.sp,
                fontFamily = FontFamily(Font(resId = R.font.harry)),
                color = Color.White
            ),
        )
        Spacer(modifier = modifier.height(16.dp))
        friendRequests.forEach { friendRequest ->
            UserCard(
                composable = {
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .size(50.dp)
                            .padding(6.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(53, 139, 63), // Background color
                            contentColor = Color.White   // Text/icon color
                        ),
                        border = BorderStroke(2.dp, Color.White),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.check),
                            contentDescription = "Accept request",
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
                            containerColor = Color(176, 48, 50), // Background color
                            contentColor = Color.White   // Text/icon color
                        ),
                        border = BorderStroke(2.dp, Color.White),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.cross),
                            contentDescription = "Reject request",
                            modifier = modifier.size(16.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                },
                modifier = modifier,
                picture = R.drawable.face,
                wand = R.drawable.wand_side,
                username = friendRequest
            )
            Spacer(modifier = modifier.height(16.dp))
        }

        Spacer(modifier = modifier.height(16.dp))

        Text(
            text = "YOUR FRIENDS",
            style = TextStyle(
                fontSize = 36.sp,
                fontFamily = FontFamily(Font(resId = R.font.harry)),
                color = Color.White
            ),
        )
        Spacer(modifier = modifier.height(16.dp))
        friends.forEach { friend ->
            UserCard(
                composable = { RemoveButton(modifier) },
                modifier = Modifier.clickable{
                    navController!!.navigate(Screens.SpellsSocial.route)
                },
                picture = R.drawable.face,
                wand = R.drawable.wand_side,
                username = friend
            )
            Spacer(modifier = modifier.height(16.dp))
        }

    }
}

@Preview
@Composable
fun FriendsListScreenPreview() {
    FriendsListScreen(Modifier, null, null)
}