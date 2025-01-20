package com.cmapp.ui

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.cmapp.ui.screens.utils.ErrorScreen
import com.cmapp.ui.screens.utils.LoadingScreen
import com.cmapp.ui.screens.utils.ScreenSkeleton
import com.cmapp.ui.states.PotterUiState
import com.cmapp.ui.states.UnsplashUiState
import com.cmapp.ui.theme.CMAppTheme
import com.cmapp.ui.viewmodels.PotterViewModel
import com.cmapp.ui.viewmodels.UnsplashViewModel

@Composable
fun DataPage(modifier: Modifier = Modifier, context: Context?,  navController: NavHostController?) {

    val potterViewModel: PotterViewModel = viewModel()
    val unsplashViewModel: UnsplashViewModel = viewModel()

    val potions by potterViewModel.potions.collectAsState()
    val spells by potterViewModel.spells.collectAsState()

    val scrollState = rememberScrollState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState)
    ) {
        if (potions == null) {
            Text("Fetching potions...")
        } else {
            Text("API Response: $potions")
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (spells == null) {
            Text("Fetching spells...")
        } else {
            Text("API Response: $spells")
        }
    }
}
