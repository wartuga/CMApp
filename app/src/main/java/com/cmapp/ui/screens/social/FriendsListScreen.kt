package com.cmapp.ui.screens.social

import android.content.Context
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cmapp.R
import com.cmapp.model.data.DataBaseHelper.getFriendRequests
import com.cmapp.model.data.DataBaseHelper.getFriends
import com.cmapp.model.data.DataBaseHelper.getProfiles
import com.cmapp.model.data.StorageHelper.getUsername
import com.cmapp.model.data.getSuggestions
import com.cmapp.model.domain.database.Profile
import com.cmapp.navigation.Screens
import com.cmapp.ui.screens.utils.AcceptRejectButtons
import com.cmapp.ui.screens.utils.RemoveButton
import com.cmapp.ui.screens.utils.RequestButton
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
        composable = { FriendsListScreenContent(modifier, navController, context!!) },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FriendsListScreenContent(modifier: Modifier, navController: NavHostController?, context: Context) {

    val scrollState = rememberScrollState()
    var searchText by remember {mutableStateOf("") }

    var suggestedProfiles by remember { mutableStateOf<List<Profile>>(mutableListOf()) }
    getProfiles(){ profilesDb -> suggestedProfiles = getSuggestions(getUsername(context), profilesDb, searchText) }

    var requests by remember { mutableStateOf<List<Profile>>(mutableListOf()) }
    getFriendRequests(getUsername(context)){ requestsDb -> requests = requestsDb }

    var friends by remember { mutableStateOf<List<Profile>>(mutableListOf()) }
    getFriends(getUsername(context)){ friendsDb -> friends = friendsDb }

    Column(
        modifier = modifier.fillMaxSize().verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = modifier.height(24.dp))

        // Search bar
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .border(
                    BorderStroke(3.dp, Color.White),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp)
                )
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically, // Aligns the icon and text field vertically
            ) {
                // Icon
                Icon(
                    painter = painterResource(id = R.drawable.magnifying_glass), // Replace with your icon
                    contentDescription = "Search icon",
                    tint = Color.White,
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                        .size(30.dp) // Adds some space between the icon and the text field
                )

                // Text Input (TextField)
                TextField(
                    value = searchText, // Replace with your TextField value
                    onValueChange = { newText -> searchText = newText },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 30.sp,
                        textDecoration = TextDecoration.Underline,
                        fontFamily = FontFamily(Font(resId = R.font.harry)),
                        color = Color.White
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent, // Makes background transparent
                        focusedIndicatorColor = Color.Transparent, // Hides the indicator
                        unfocusedIndicatorColor = Color.Transparent // Hides the indicator when unfocused
                    )
                )
            }
        }

        if (searchText.isNotEmpty()) {

            Spacer(modifier = modifier.height(16.dp))

            suggestedProfiles.forEach { profile ->
                profile.username?.let {
                    UserCard(
                        composable = {RequestButton(modifier, getUsername(context), profile.username!!)},
                        modifier = modifier,
                        picture = profile.photo!!,
                        wand = profile.wandSide!!,
                        username = profile.username!!
                    )
                    Spacer(modifier = modifier.height(16.dp))
                }
            }
        }

        else {

            Spacer(modifier = modifier.height(32.dp))

            Text(
                text = "FRIEND REQUESTS",
                style = TextStyle(
                    fontSize = 36.sp,
                    fontFamily = FontFamily(Font(resId = R.font.harry)),
                    color = Color.White
                ),
            )
            Spacer(modifier = modifier.height(16.dp))
            requests.forEach { profile ->
                profile.username?.let {
                    UserCard(
                        composable = { AcceptRejectButtons(modifier, getUsername(context), profile.username!!) },
                        modifier = modifier,
                        picture = profile.photo!!,
                        wand = profile.wandSide!!,
                        username = it
                    )
                }
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
            friends.forEach { profile ->
                profile.username?.let {
                    UserCard(
                        composable = { RemoveButton(modifier, getUsername(context), profile.username!!, navController!!) },
                        modifier = Modifier.clickable {

                            //friend.username
                            navController!!.navigate(
                                Screens.SpellsSocial.route.replace(
                                    oldValue = "{friendUsername}",
                                    newValue = profile.username!!
                                )
                            )
                        },
                        picture = profile.photo!!,
                        wand = profile.wandSide!!,
                        username = it
                    )
                }
                Spacer(modifier = modifier.height(16.dp))
            }
        }

    }
}

@Preview
@Composable
fun FriendsListScreenPreview() {
    FriendsListScreen(Modifier, null, null)
}