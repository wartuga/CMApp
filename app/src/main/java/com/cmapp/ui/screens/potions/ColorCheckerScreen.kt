package com.cmapp.ui.screens.potions

import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
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
import com.cmapp.navigation.Screens
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun ColorCheckerScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController?,
    context: Context?,
    potionColor: String?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val decodedColor = try {
            potionColor?.let { URLDecoder.decode(it, StandardCharsets.UTF_8.toString()) }
        } catch (e: Exception) {
            null
        }

        //println(potionColor!!)
        CameraCaptureContent(navController, potionColor!!)
    }
}

@Composable
fun CameraCaptureContent(navController: NavHostController?, potionColorName:String) {
    var validatedColor = false
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var dominantColor by remember { mutableStateOf<Color?>(null) }
    //val potionColor = Color.Green
    val potionColor = getColorFromName(potionColorName)
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
        Button(
            onClick = {
                imageCaptureLauncher.launch(null)
            }
        ) {
            Text("Back")
        }

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
            //Display
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(16.dp)
                        .background(dominantColor!!)
                )
                Text(text = "Dominant Color: $dominantColor")
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(16.dp)
                        .background(potionColor!!)
                )
                Text(text = "Dominant Color: $dominantColor")
    //Check if color is correct

                val similar = areColorsSimilar(dominantColor!!, potionColor!!)

                if(similar){
                    validatedColor = true
                }

                val toast = Toast.makeText(LocalContext.current, similar.toString(), Toast.LENGTH_SHORT) // in Activity
                toast.show()

        }

        Spacer(modifier = Modifier.height(16.dp))
        if(validatedColor){
            Button(
                onClick = {
                    navController!!.navigate(Screens.LearnPotions.route)
                }
            ) {
                Text("Learn new potion")
            }
        }
        else{
            Button(
                onClick = {
                    imageCaptureLauncher.launch(null)
                }
            ) {
                Text("Re-validate")
            }
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

// Euclidean distance between two colors
fun colorDistance(color1: Color, color2: Color): Float {
    // RGB components
    val r1 = (color1.red * 255).toInt()
    val g1 = (color1.green * 255).toInt()
    val b1 = (color1.blue * 255).toInt()

    val r2 = (color2.red * 255).toInt()
    val g2 = (color2.green * 255).toInt()
    val b2 = (color2.blue * 255).toInt()

    // Euclidean distance
    return sqrt(
        ((r1 - r2).toFloat().pow(2)) +
                ((g1 - g2).toFloat().pow(2)) +
                ((b1 - b2).toFloat().pow(2))
    )
}

// Check based on a distance threshold
fun areColorsSimilar(color1: Color, color2: Color, threshold: Float = 200f): Boolean {
    val distance = colorDistance(color1, color2)
    return distance <= threshold
}

fun getColorFromNameIgnoreCase(colorName: String): Color? {
    return Color::class.java.declaredFields
        .firstOrNull { it.name.equals(colorName, ignoreCase = true) }
        ?.let { field -> field.get(null) as? Color }
}
val colorNameMap = mapOf(
    "Blue" to Color.Blue,
    "Red" to Color.Red,
    "Green" to Color.Green,
    "Yellow" to Color.Yellow,
    "Black" to Color.Black,
    "White" to Color.White,
    "Gray" to Color.Gray,
    "Cyan" to Color.Cyan,
    "Magenta" to Color.Magenta
    // Add more colors as needed
)

// Function to get a Color object from a string
fun getColorFromName(colorName: String): Color? {
    return colorNameMap[colorName]
}

@Preview
@Composable
fun ColorCheckerScreenPreview() {
    ColorCheckerScreen(
        context = LocalContext.current, modifier = Modifier, navController = null,
        potionColor = null
    )
}
