package com.cmapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.cmapp.ui.screens.utils.ErrorScreen
import com.cmapp.ui.screens.utils.LoadingScreen
import com.cmapp.ui.states.PotterUiState
import com.cmapp.ui.states.UnsplashUiState

@Composable
fun HomeScreen(
    uiState: PotterUiState,
    modifier: Modifier
) {
    when {
        uiState is PotterUiState.Loading -> LoadingScreen()
        uiState is PotterUiState.Success -> {
            Column(modifier = modifier.fillMaxSize()) {
                Text(
                    text = "Hello Android",
                    modifier.align(alignment = Alignment.CenterHorizontally)
                )
                Text(uiState.potions)
            }
        }
        uiState is PotterUiState.Error -> ErrorScreen()
    }
}