package com.cmapp.ui.screens.wand

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.cmapp.ui.screens.utils.ScreenSkeleton

@Composable
fun WandScreen(
    modifier: Modifier
) {
    ScreenSkeleton(
        composable = { WandScreenContent(modifier) },
        modifier = modifier
    )
}

@Preview
@Composable
fun PreviewWandScreen() {
    WandScreen(
        modifier = Modifier
    )
}