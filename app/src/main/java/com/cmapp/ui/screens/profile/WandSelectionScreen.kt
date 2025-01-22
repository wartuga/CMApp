package com.cmapp.ui.screens.profile

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cmapp.R
import com.cmapp.model.data.DataBaseHelper
import com.cmapp.model.data.StorageHelper.getUsername
import com.cmapp.ui.screens.utils.ProfileButtons
import com.cmapp.ui.screens.utils.ScreenSkeleton
import com.cmapp.ui.screens.utils.SwapImage
import com.cmapp.ui.screens.utils.UserCard

@Composable
fun WandSelectionScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController?,
    context: Context?
) {
    ScreenSkeleton(
        navController = navController,
        composable = {
            WandSelectionScreenContent(modifier, context!!, navController)
        },
        modifier = modifier
    )
}

@Composable
private fun WandSelectionScreenContent(modifier: Modifier, context: Context, navController: NavHostController?) {

    var profileImage by remember { mutableStateOf<String?>("https://firebasestorage.googleapis.com/v0/b/hogwarts-apprentice.firebasestorage.app/o/face.jpg?alt=media&token=58ef618c-4dfe-4e1d-a58c-9bb65b5810b5") }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        profileImage = uri.toString()
        DataBaseHelper.updateProfilePhoto(getUsername(context), uri.toString())
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = modifier.height(24.dp))
        profileImage?.let {
            UserCard(
                composable = {ProfileButtons(context, modifier, navController!!, galleryLauncher) },
                modifier = modifier,
                //ALTERAR
                picture = it,
                wand = "",
                username = getUsername(context)
            )
        }
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
    WandSelectionScreen(modifier = Modifier, null, null)
}