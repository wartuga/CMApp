package com.cmapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cmapp.R
import com.cmapp.ui.screens.HomeScreen
import com.cmapp.ui.theme.CMAppTheme
import com.cmapp.ui.viewmodels.PotterViewModel

@Composable
fun HomePage(modifier: Modifier = Modifier) {
    Scaffold (
        bottomBar = { NavigationBar(modifier) }
    ) { padding ->
        val viewModel: PotterViewModel = viewModel()
        Box(modifier = modifier.padding(padding).fillMaxSize()){
            Image(
                painter = painterResource(R.drawable.bg),
                contentDescription = "background",
                contentScale = ContentScale.Crop
            )
            HomeScreen(viewModel.uiState, modifier)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CMAppTheme {
        HomePage()
    }
}