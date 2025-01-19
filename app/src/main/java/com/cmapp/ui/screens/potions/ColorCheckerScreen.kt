package com.cmapp.ui.screens.potions

import android.content.Context
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ColorCheckerScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController?,
    context: Context?,
    potionId: Int?
) {

    // UI displays immediately, then requests permission
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CameraCaptureContent()
    }
}

@Composable
fun CameraCaptureContent() {
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var dominantColor by remember { mutableStateOf<Color?>(null) }

    val imageCaptureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        imageBitmap = bitmap
        dominantColor = bitmap?.let { calculateDominantColor(it) }
    }
    // Trigger the camera capture on first display
    LaunchedEffect(Unit) {
        imageCaptureLauncher.launch(null)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (imageBitmap != null) {
            Image(
                bitmap = imageBitmap!!.asImageBitmap(),
                contentDescription = "Captured Image",
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp)
            )
        }

        if (dominantColor != null) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .padding(16.dp)
                    .background(dominantColor!!)
            )
            Text(text = "Dominant Color: $dominantColor")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                imageCaptureLauncher.launch(null)
            }
        ) {
            Text("Re-validate")
        }
    }
}

fun calculateDominantColor(bitmap: Bitmap): Color {
    val pixelColors = mutableListOf<Int>()
    for (x in 0 until bitmap.width) {
        for (y in 0 until bitmap.height) {
            pixelColors.add(bitmap.getPixel(x, y))
        }
    }

    val averageRed = pixelColors.map { Color(it).red }.average()
    val averageGreen = pixelColors.map { Color(it).green }.average()
    val averageBlue = pixelColors.map { Color(it).blue }.average()

    return Color(
        red = averageRed.toFloat(),
        green = averageGreen.toFloat(),
        blue = averageBlue.toFloat()
    )
}

@Preview
@Composable
fun ColorCheckerScreenPreview() {
    ColorCheckerScreen(
        context = LocalContext.current, modifier = Modifier, navController = null,
        potionId = 1
    )
}
