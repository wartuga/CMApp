package com.cmapp.ui.screens.wand

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cmapp.R
import com.cmapp.ui.screens.utils.ScreenSkeleton
import kotlinx.coroutines.delay
import kotlin.math.roundToInt
import kotlin.random.Random
import kotlin.random.nextInt

@Composable
fun MovementScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController?,
    context: Context?
) {
    ScreenSkeleton(
        navController = navController,
        composable = { MovementScreenContent(modifier, context) },
        modifier = modifier
    )
}

@Composable
private fun MovementScreenContent(modifier: Modifier, context: Context?) {

    val configuration = LocalConfiguration.current

    var imageRotationAngle by remember { mutableFloatStateOf(0f) }
    var wandDirection by remember { mutableStateOf("up") }
    var lastWandDirection by remember { mutableStateOf(wandDirection) }
    var lastTimestamp by remember { mutableLongStateOf(System.currentTimeMillis()) }

    var move by remember { mutableStateOf("up-right") }
    var moveColor by remember { mutableStateOf(Color.White) }

    var lastValueX by remember { mutableFloatStateOf(0F) }
    var positionX by remember { mutableFloatStateOf(0F) }

    if(context != null) {
        // Initialize SensorManager and Sensor
        LaunchedEffect(Unit) {
            val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            val rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR)
            var pivotAngle: Float? = null

            // SensorEventListener to update the rotation angle
            val rotationListener = object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent?) {
                    if (event != null && event.sensor.type == Sensor.TYPE_GAME_ROTATION_VECTOR) {
                        val sensorZ = event.values[2]
                        if(pivotAngle == null){
                            pivotAngle = sensorZ
                        }

                        val rotationAngle = pivotAngle?.minus(sensorZ)

                        imageRotationAngle = rotationAngle!! * 50f

                        wandDirection = when {
                            rotationAngle > 0.35 -> "right"
                            rotationAngle in 0.10..0.35 -> "up-right"
                            rotationAngle in -0.10..0.10 -> "up"
                            rotationAngle in -0.35..-0.10 -> "up-left"
                            else -> "left"  // rotationAngle < -0.35
                        }

                        if (wandDirection != lastWandDirection) {
                            lastWandDirection = wandDirection
                            lastTimestamp = System.currentTimeMillis() // Reset timestamp on change
                        }
                    }
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
            }

            // SensorEventListener to update the image X position
            val accelerometerListener = object: SensorEventListener {
                val screenSize = configuration.screenWidthDp
                val imageSize = 32
                override fun onSensorChanged(event: SensorEvent?) {
                    if (event != null && event.sensor.type == Sensor.TYPE_ACCELEROMETER){
                        val sensorX = event.values[0]
                        // sensorX > 0 -> right; sensorX < 0 -> left

                        if (lastValueX != sensorX){
                            positionX += sensorX * 10
                            lastValueX = sensorX

                            // limits the position of the image between 0px and (screen size - image size)px
                            positionX = positionX.coerceIn(0F, (screenSize - imageSize).toFloat())
                        }
                    }
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
            }

            // Register listeners
            // Rotation Vector
            sensorManager.registerListener(
                rotationListener,
                rotationSensor,
                SensorManager.SENSOR_DELAY_UI
            )
            // Accelerometer
            sensorManager.registerListener(
                accelerometerListener,
                accelerometer,
                SensorManager.SENSOR_DELAY_UI
            )
        }

        // Check if the direction is stable for 2 seconds, then wait 1 second and print
        LaunchedEffect(wandDirection) {
            while (true) {
                delay(100) // Check every 100ms
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastTimestamp >= 1500L && wandDirection == move) {
                    moveColor = Color.Green
                    delay(250L) // Wait
                    move = when (Random.nextInt(1..5)) {
                        1 -> "right"
                        2 -> "up-right"
                        3 -> "up"
                        4 -> "up-left"
                        else -> "left" // Default image
                    }
                    moveColor = Color.White

                    lastTimestamp = System.currentTimeMillis()
                }
            }
        }
    }

    // receber varinha do user ou ir buscar a varinha do user
    val seconds = 23 // tempo do feitiço
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = modifier.padding(30.dp)) {
            Text(
                text = "Feitiço",
                color = Color.White,
                fontSize = 36.sp
            )
        }
        Row(modifier = modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = R.drawable.timer),
                contentDescription = "clock",
                modifier = Modifier
                    .size(30.dp)
                    .padding(end = 4.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )
            Text(
                text = "${seconds}s",
                color = Color.White,
                fontSize = 20.sp
            )
        }
        Row(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val imageResource = when (move) {
                "right" -> R.drawable.arrow_right
                "up-right" -> R.drawable.arrow_up_right
                "up" -> R.drawable.arrow_up
                "up-left" -> R.drawable.arrow_up_left
                "left" -> R.drawable.arrow_left
                else -> R.drawable.arrow_right // Default image
            }

            Image(
                painter = painterResource(id = imageResource),
                contentDescription = "Movimento",
                modifier = Modifier.size(50.dp),
                colorFilter = ColorFilter.tint(moveColor)
            )
            //}
        }
        Box(
            modifier = Modifier.fillMaxSize() // Make the Box fill the screen
        ) {
            Image(
                painter = painterResource(id = R.drawable.wand), // Replace with your image resource
                contentDescription = "Image at Bottom Center",
                modifier = Modifier.align(Alignment.BottomCenter)
                    .size(400.dp)
                    .padding(bottom = 32.dp)
                    .graphicsLayer(
                        rotationZ = imageRotationAngle,
                        transformOrigin = TransformOrigin(0.5f, 0.9f)
                    ) // Align image at the bottom center
                    .offset{ IntOffset(positionX.roundToInt(), 0) }
            )

        }
    }
}

@Preview
@Composable
fun PreviewWandScreen() {
    MovementScreen(
        modifier = Modifier,
        null,
        null
    )
}