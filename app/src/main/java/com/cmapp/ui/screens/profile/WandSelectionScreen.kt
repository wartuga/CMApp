package com.cmapp.ui.screens.profile

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cmapp.R
import com.cmapp.model.data.DataBaseHelper
import com.cmapp.model.data.DataBaseHelper.getProfile
import com.cmapp.model.data.StorageHelper.getUsername
import com.cmapp.model.domain.database.Profile
import com.cmapp.ui.screens.utils.ProfileButtons
import com.cmapp.ui.screens.utils.ScreenSkeleton
import com.cmapp.ui.screens.utils.SwapWand
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

    var profileImage by remember { mutableStateOf<String?>("") }

    var profile by remember { mutableStateOf<Profile>(Profile()) }
    getProfile(getUsername(context)){ profileDb ->
        profile = profileDb
        profileImage = profileDb.photo
    }

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
        profile.username?.let {
            UserCard(
                composable = {ProfileButtons(context, modifier, navController!!, galleryLauncher) },
                modifier = modifier,
                picture = profileImage!!,
                wand = profile.wandSide!!,
                username = profile.username!!
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
        profile.wandFront?.let {
            SwapWand(username = getUsername(context), initialWand = profile.wandFront!!, modifier = modifier)
        }
    }
}

@Preview
@Composable
fun WandSelectionScreenPreview() {
    WandSelectionScreen(modifier = Modifier, null, null)
}