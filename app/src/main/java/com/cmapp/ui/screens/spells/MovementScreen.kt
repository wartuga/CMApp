package com.cmapp.ui.screens.spells

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.cmapp.model.data.StorageHelper.getUsername
import com.cmapp.model.data.addLearnedSpell
import com.cmapp.model.data.getSpellAsync
import com.cmapp.model.domain.database.Spell
import com.cmapp.ui.screens.utils.ScreenSkeleton
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@Composable
fun MovementScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController?,
    context: Context?,
    spellKey: String?
) {
    ScreenSkeleton(
        navController = navController,
        composable = { MovementScreenContent(modifier, navController, context, spellKey!!) },
        modifier = modifier
    )
}

@Composable
private fun MovementScreenContent(modifier: Modifier, navController: NavHostController?, context: Context?, spellKey: String) {



    val configuration = LocalConfiguration.current

    var imageRotationAngle by remember { mutableFloatStateOf(0f) }
    var wandDirection by remember { mutableStateOf("up") }
    var lastWandDirection by remember { mutableStateOf(wandDirection) }
    var lastTimestamp by remember { mutableLongStateOf(System.currentTimeMillis()) }
    var moveColor by remember { mutableStateOf(Color.White) }

    var lastValueX by remember { mutableFloatStateOf(0F) }
    var positionX by remember { mutableFloatStateOf(0F) }

    // receber varinha do user ou ir buscar a varinha do user

    var spell by remember { mutableStateOf(Spell()) }
    var previousMove: String? by remember { mutableStateOf(null) }
    var move by remember { mutableStateOf("up") }
    var nextMove by remember { mutableStateOf("") }
    var remainingTime by remember { mutableIntStateOf(59) }

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

        // Check if the direction is stable for 1.5 seconds, then wait and print
        LaunchedEffect(Unit) {
            val spellDb = getSpellAsync(spellKey)
            var i = 0

            spell = spellDb!!
            move = spellDb.movements[i]
            nextMove = spellDb.movements[++i]
            remainingTime = spellDb.time!!

            Log.d("spell", spell.toString())
            var time = 0    // time in milliseconds
            val spellTime = spell.time!! * 1000
            while (i < spell.movements.size) {
                delay(100) // Check every 100ms
                time += 100
                val currentTime = System.currentTimeMillis()

                remainingTime = spellTime - time

                if (remainingTime < 0) {
                    Toast.makeText(context, "You run out of time", Toast.LENGTH_LONG).show()
                    navController?.popBackStack()
                    break
                }

                if (currentTime - lastTimestamp >= 1500L && wandDirection == move) {
                    moveColor = Color.Green
                    delay(250L) // Wait

                    if (i < spell.movements.size) {
                        previousMove = move
                        move = spell.movements[i++]
                        if (i < spell.movements.size)
                            nextMove = spell.movements[i]
                        else
                            nextMove = ""
                        moveColor = Color.White
                    } else {
                        Toast.makeText(context, "You learned a new spell", Toast.LENGTH_LONG).show()
                        val username: String = getUsername(context)
                        addLearnedSpell(username, spellKey)
                        navController?.popBackStack()
                        break
                    }

                    lastTimestamp = System.currentTimeMillis()
                }
            }
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = modifier.padding(30.dp)) {
            spell.name?.let {
                Text(
                    text = it,
                    color = Color.White,
                    fontSize = 36.sp
                )
            }
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
                text = "${remainingTime / 1000}s",
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
            // previous movement
            val previousRes = when (previousMove) {
                "right" -> R.drawable.arrow_right
                "up-right" -> R.drawable.arrow_up_right
                "up" -> R.drawable.arrow_up
                "up-left" -> R.drawable.arrow_up_left
                "left" -> R.drawable.arrow_left
                else -> null // return null for invalid cases
            }
            if(previousRes != null){
                Image(
                    painter = painterResource(id = previousRes),
                    contentDescription = "Movimento",
                    modifier = Modifier.size(50.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
            } else {
                Spacer(Modifier.size(50.dp))
            }
            // current movement
            Image(
                painter = painterResource(id =
                    when (move) {
                        "right" -> R.drawable.arrow_right
                        "up-right" -> R.drawable.arrow_up_right
                        "up" -> R.drawable.arrow_up
                        "up-left" -> R.drawable.arrow_up_left
                        "left" -> R.drawable.arrow_left
                        else -> R.drawable.arrow_up
                    }
                ),
                contentDescription = "Movimento",
                modifier = Modifier.size(50.dp),
                colorFilter = ColorFilter.tint(moveColor)
            )
            // next movement
            val nextRes = when (nextMove) {
                "right" -> R.drawable.arrow_right
                "up-right" -> R.drawable.arrow_up_right
                "up" -> R.drawable.arrow_up
                "up-left" -> R.drawable.arrow_up_left
                "left" -> R.drawable.arrow_left
                else -> null // return null for invalid cases
            }
            if(nextRes != null){
                Image(
                    painter = painterResource(id = nextRes),
                    contentDescription = "Movimento",
                    modifier = Modifier.size(50.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
            } else {
                Spacer(Modifier.size(50.dp))
            }
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
        null,
        null
    )
}