package com.lab4.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lab4.ui.theme.CMAppTheme

@Composable
fun HomePage(modifier: Modifier = Modifier) {

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CMAppTheme {
        HomePage()
    }
}