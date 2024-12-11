package com.cmapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cmapp.ui.screens.HomeScreen
import com.cmapp.ui.screens.utils.ScreenSkeleton
import com.cmapp.ui.theme.CMAppTheme
import com.cmapp.ui.viewmodels.UnsplashViewModel

@Composable
fun HomePage(modifier: Modifier = Modifier) {
    val viewModel: UnsplashViewModel = viewModel()
    ScreenSkeleton(
        { HomeScreen(viewModel.uiState, modifier) },
        modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CMAppTheme {
        HomePage()
    }
}