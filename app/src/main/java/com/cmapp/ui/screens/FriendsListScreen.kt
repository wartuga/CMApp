package com.cmapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cmapp.R
import com.cmapp.ui.screens.utils.RemoveButton
import com.cmapp.ui.screens.utils.ScreenSkeleton
import com.cmapp.ui.screens.utils.UserCard

@Composable
fun FriendsListScreen(
    modifier: Modifier
) {
    ScreenSkeleton(
        composable = { FriendsListScreenContent(modifier) },
        modifier = modifier
    )
}

@Composable
private fun FriendsListScreenContent(modifier: Modifier) {
    val friendRequests = listOf("Friend 6", "Friend 7")
    val friends = listOf("Friend 1", "Friend 2", "Friend 3", "Friend 4", "Friend 5")
    Column(
        modifier = modifier.fillMaxSize(),
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

        Text(
            text = "Friend Requests",
            color = Color.White,
            fontSize = 36.sp
        )
        friendRequests.forEach { friendRequest ->
            UserCard(
                username = friendRequest,
                composable = {
                    Button(
                        onClick = {  },
                        modifier = modifier
                            .size(40.dp)
                            .padding(6.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                        border = BorderStroke(1.dp, Color.White),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.check),
                            contentDescription = "Accept",
                            modifier = modifier.size(15.dp)
                        )
                    }
                    Button(
                        onClick = {  },
                        modifier = modifier
                            .size(40.dp)
                            .padding(6.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        border = BorderStroke(1.dp, Color.White),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.cross),
                            contentDescription = "Decline",
                            modifier = modifier.size(15.dp)
                        )
                    }
                },
                modifier
            )
        }

        Spacer(modifier = modifier.height(8.dp))

        Text(
            text = "Friends",
            color = Color.White,
            fontSize = 36.sp
        )
        friends.forEach { friend ->
            UserCard(
                username = friend,
                composable = { RemoveButton(modifier) },
                modifier
            )
        }

    }
}

@Preview
@Composable
fun FriendsListScreenPreview() {
    FriendsListScreen(Modifier)
}