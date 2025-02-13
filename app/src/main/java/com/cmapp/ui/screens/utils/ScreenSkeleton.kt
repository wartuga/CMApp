package com.cmapp.ui.screens.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.cmapp.R

@Composable
fun ScreenSkeleton(
    navController: NavHostController?,
    composable: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold (
        bottomBar = { if(navController!=null) NavigationBar(modifier, navController) }
    ) { padding ->
        Box(modifier = modifier.padding(padding).fillMaxSize()){
            Image(
                painter = painterResource(R.drawable.bg),
                contentDescription = "background",
                contentScale = ContentScale.Crop
            )
            composable()
        }
    }
}