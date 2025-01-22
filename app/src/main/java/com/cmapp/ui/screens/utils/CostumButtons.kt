package com.cmapp.ui.screens.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.cmapp.R
import com.cmapp.model.data.DataBaseHelper
import com.cmapp.model.data.DataBaseHelper.addFriends
import com.cmapp.model.data.DataBaseHelper.deleteFriendRequest
import com.cmapp.model.data.DataBaseHelper.deleteFriends
import com.cmapp.model.data.StorageHelper.setUsername
import com.cmapp.model.data.getWandsFront
import com.cmapp.model.data.getWandsSide
import com.cmapp.navigation.Screens

@Composable
fun RemoveButton(modifier: Modifier, username: String, friendUsername: String, navController: NavController) {
    Button(
        onClick = {
            deleteFriends(username, friendUsername)
            navController.navigate(Screens.FriendsSocial.route)
                  },
        modifier = modifier
            .height(40.dp)
            .width(76.dp)
            .offset(x = 2.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(176, 48, 50)),
        border = BorderStroke(2.dp, Color.White),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = "Remove",
            style = TextStyle(
                fontSize = 26.sp,
                fontFamily = FontFamily(Font(resId = R.font.harry)),
                color = Color.White
            ),
        )
    }
}

@Composable
fun ProfileButtons(context: Context, modifier: Modifier, navController: NavController, galleryLauncher: ManagedActivityResultLauncher<String, Uri?>){

    Button(
        onClick = {galleryLauncher.launch("image/*")},
        modifier = Modifier
            .size(50.dp)
            .padding(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(83, 12, 114), // Background color
            contentColor = Color.White   // Text/icon color
        ),
        border = BorderStroke(2.dp, Color.White),
        contentPadding = PaddingValues(0.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.edit),
            contentDescription = "Edit Photo",
            modifier = modifier.size(22.dp),
            colorFilter = ColorFilter.tint(Color.White)
        )
    }
    Button(
        onClick = {
            setUsername(context, "")
            navController?.navigate(Screens.Login.route)
        },
        modifier = Modifier
            .size(50.dp)
            .padding(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(83, 12, 114), // Background color
            contentColor = Color.White   // Text/icon color
        ),
        border = BorderStroke(2.dp, Color.White),
        contentPadding = PaddingValues(0.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.exit),
            contentDescription = "Exit",
            modifier = modifier.size(22.dp),
            colorFilter = ColorFilter.tint(Color.White)
        )
    }
}

@Composable
fun AcceptRejectButtons(modifier: Modifier, username: String, friendUsername: String){

    Button(
        onClick = {addFriends(username, friendUsername)},
        modifier = Modifier
            .size(50.dp)
            .padding(4.dp),
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
        onClick = { deleteFriendRequest(friendUsername, username) },
        modifier = Modifier
            .size(50.dp)
            .padding(4.dp),
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
}



@Composable
fun RequestButton(modifier: Modifier, username: String, friendUsername: String) {

    //CANCEL

    Button(
        onClick = {DataBaseHelper.addFriendRequest(username, friendUsername)},
        modifier = modifier
            .height(40.dp)
            .width(76.dp)
            .offset(x = 2.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(83, 12, 114)),
        border = BorderStroke(2.dp, Color.White),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = "Request",
            style = TextStyle(
                fontSize = 26.sp,
                fontFamily = FontFamily(Font(resId = R.font.harry)),
                color = Color.White
            ),
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

@Composable
fun SwapWand(username: String, initialWand: String, modifier: Modifier){

    val wandsFront = getWandsFront()
    val wandsSide = getWandsSide()

    var currentIdx by remember { mutableIntStateOf(wandsFront.indexOf(initialWand)) }
    var wandImage by remember { mutableStateOf(wandsFront[currentIdx]) }

    if(currentIdx != -1) {
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
            Button(
                onClick = {DataBaseHelper.updateWand(username, wandsFront[currentIdx], wandsSide[currentIdx])},
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