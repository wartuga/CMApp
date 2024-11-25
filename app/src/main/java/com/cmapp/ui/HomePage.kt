package com.cmapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.cmapp.R
import com.cmapp.ui.theme.CMAppTheme

@Composable
fun HomePage(modifier: Modifier = Modifier) {
    Scaffold (
        bottomBar = { NavigationBar(modifier) }
    ) { padding ->
        Box(modifier = modifier.fillMaxSize()){
            Image(
                painter = painterResource(R.drawable.bg),
                contentDescription = "background",
                contentScale = ContentScale.Crop
            )
            Column(modifier = modifier.padding(padding).fillMaxSize()) {
                Text(text = "Hello Android", modifier.align(alignment = Alignment.CenterHorizontally))
            }
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