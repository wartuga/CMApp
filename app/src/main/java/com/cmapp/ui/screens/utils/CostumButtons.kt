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
import com.cmapp.model.data.DataBaseHelper.areFriends
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
            navController.navigate(Screens.Login.route)
            setUsername(context, "")
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

    var buttonText by remember { mutableStateOf("Request") }
    var buttonColor by remember { mutableStateOf(Color(83, 12, 114)) }

    areFriends(username, friendUsername) { friends ->
        if(friends){
            buttonText = "Friends"
            buttonColor = Color(83, 12, 114)
        }
    }

    Button(
        onClick = {
            if(buttonText == "Cancel"){
                buttonText = "Request"
                buttonColor = Color(176, 48, 50)
                DataBaseHelper.deleteFriendRequest(username, friendUsername)
            }
            else if(buttonText == "Request"){
                buttonText = "Cancel"
                buttonColor = Color(126, 119, 130)
                DataBaseHelper.addFriendRequest(username, friendUsername)
            }
                  },
        modifier = modifier
            .height(40.dp)
            .width(76.dp)
            .offset(x = 2.dp),
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        border = BorderStroke(2.dp, Color.White),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = buttonText,
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
