package com.cmapp.ui.screens.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cmapp.R

@Composable
fun UserCard(
    username: String,
    composable: @Composable () -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile_bg),
            contentDescription = "profile background",
            contentScale = ContentScale.FillWidth
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.profile_icon),
                contentDescription = "profile picture",
                modifier = Modifier
                    .size(60.dp)
                    .padding(8.dp)
            )
            Column(modifier = modifier.padding(top = 8.dp, bottom = 8.dp)) {
                Text(text = username)
                Text(text = "Wand Image")
            }
            composable()
        }
    }
}