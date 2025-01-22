package com.cmapp.ui.screens.potions

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Looper
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cmapp.R
import com.cmapp.model.data.DataBaseHelper.getPotion
import com.cmapp.model.domain.database.Potion
import com.cmapp.navigation.Screens
import com.cmapp.ui.screens.utils.ScreenSkeleton
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import kotlin.random.Random

const val TITLE_SIZE = 28
const val FONT_SIZE = 20
//const val MAP_SIZE = 330 //It takes up the remaining space in the screen
const val PADDING = 5

@Composable
fun MapScreen(modifier: Modifier = Modifier, navController: NavHostController?, context: Context?, potionKey: String?) {
    ScreenSkeleton(
        navController = navController,
        composable = {
            CheckAndRequestLocationPermission(context = context ?: return@ScreenSkeleton) {
                MapScreenContent(modifier, navController, context, potionKey!!)
            }
        },
        modifier
    )
}

@Composable
fun CheckAndRequestLocationPermission(
    context: Context,
    content: @Composable () -> Unit
) {
    val permission = android.Manifest.permission.ACCESS_FINE_LOCATION
    var permissionGranted by remember {
        mutableStateOf(
            androidx.core.content.ContextCompat.checkSelfPermission(
                context,
                permission
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        )
    }

    if (permissionGranted) {
        // If permission is previously granted - display the content
        content()
    } else {
        // Request permission using a launcher
        val permissionLauncher = androidx.activity.compose.rememberLauncherForActivityResult(
            contract = androidx.activity.result.contract.ActivityResultContracts.RequestPermission()
        ) { isGranted -> permissionGranted = isGranted }

        LaunchedEffect(Unit) {
            permissionLauncher.launch(permission)
        }
        // If permission is declined:
        Text("Permission is required to display the map.")
    }
}

@Composable
private fun MapScreenContent(modifier: Modifier, navController: NavHostController?,context: Context?, potionKey: String) {

    var potion by remember { mutableStateOf<Potion>(Potion()) } //Ir buscar a pocao a base de dados
    getPotion(potionKey){ potionDb -> potion = potionDb }
    val potionColor = potion.color
    var ingredients = listOf("")
    potion.ingredients?.let{
        ingredients = listOf(it)
        if(it.contains(","))
            ingredients = it.split(",")
    }

    val context = LocalContext.current
    var userLocation by remember { mutableStateOf<LatLng?>(null) }
    TrackUserLocation(context) { location ->
        userLocation = location
    }

    // Generate places for each ingredient ONLY for the first location
    val placesByIngredient = remember { mutableStateOf<Map<String, List<LatLng>>>(emptyMap()) }
    LaunchedEffect(userLocation) {
        if (userLocation != null && placesByIngredient.value.isEmpty()) {
            val generatedPlaces = ingredients.associateWith { ingredient ->
                generateRandomPlaces(5, userLocation!!)
            }
            placesByIngredient.value = generatedPlaces
        }
    }

    Column(

        modifier = modifier.fillMaxSize().fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = modifier.padding(PADDING.dp)) {
            potion.name?.let {
                Text(
                    text = it,
                    color = Color.White,
                    fontSize = TITLE_SIZE.sp
                )
            }
        }
        Row(modifier = modifier.padding(PADDING.dp)) {
            potion.description?.let {
                Text(
                    text = it,
                    color = Color.White,
                    fontSize = FONT_SIZE.sp
                )
            }
        }
        Row(modifier = modifier.padding(PADDING.dp)) {
           potionColor?.let {
                Text(
                    text = "Color: $it",
                    color = Color.White,
                    fontSize = FONT_SIZE.sp
                )
            }
        }
        Row(modifier = modifier.padding(PADDING.dp)) {
            Column {
                Row {
                    Text(
                        text = "Ingredients:",
                        color = Color.White,
                        fontSize = FONT_SIZE.sp
                    )
                }
                ingredients.forEachIndexed { idx, ingredient ->
                    Row {
                        Text(
                            text = "${idx + 1}. $ingredient",
                            color = Color.White,
                            fontSize = FONT_SIZE.sp
                        )
                    }
                }
            }
        }
        Row(modifier = modifier.padding(PADDING.dp)) {
            Text(
                text = "You may find them in these places",
                color = Color.White,
                fontSize = FONT_SIZE.sp
            )
        }
        Row(modifier = modifier.padding(PADDING.dp).weight(1f) ) {
            val context = LocalContext.current
            var uiSettings by remember { mutableStateOf(MapUiSettings(myLocationButtonEnabled = true)) }
            var mapProperties by remember {
                mutableStateOf(MapProperties(mapType = MapType.NORMAL, isMyLocationEnabled = true))
            }

            val cameraPositionState = rememberCameraPositionState()

            // Track the user's location dynamically
            var userLocation by remember { mutableStateOf<LatLng?>(null) }

            TrackUserLocation(context) { location ->
                userLocation = location
            }
            //val places = userLocation?.let { generateRandomPlaces(5, it) } ?: emptyList()
            // Generate random places once the user location is available
           val places = remember(userLocation) {
                userLocation?.let { generateRandomPlaces(5, it) } ?: emptyList()
            }

            // Update camera position when the user moves - this isn't working
//            TrackUserLocation(context) { userLocation ->
//                cameraPositionState.position = CameraPosition.fromLatLngZoom(userLocation, 30.0F)
//            }
            GoogleMap(
                modifier = Modifier
                    .weight(1f) // Takes remaining vertical space
                    .fillMaxWidth(),//.height(MAP_SIZE.dp),//.fillMaxSize(),
                properties = mapProperties,
                uiSettings = uiSettings,
                cameraPositionState = cameraPositionState
            ) {
                /*userLocation?.let {
                    Marker(
                        position = it,
                        title = "You are here",
                        snippet = "Current location",
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
                    )
                }*/
                // Place markers for the random locations
                placesByIngredient.value.forEach { (ingredient, places) ->
                    val markerBitmap = createNumberBitmap(context, ingredients.indexOf(ingredient) + 1)
                    places.forEach { place ->
                        Marker(
                            position = place,
                            title = ingredient,
                            icon = BitmapDescriptorFactory.fromBitmap(markerBitmap)
                        )
                    }
                }
            }
        }
        Row(modifier = modifier.padding(PADDING.dp)) {
            Text(
                text = "Mix them in the cauldron until you achieve the right color",
                color = Color.White,
                fontSize = FONT_SIZE.sp
            )
        }
        Row(modifier = modifier.padding(PADDING.dp)) {
            Text(
                text = "Validate the potion with a photo",
                color = Color.White,
                fontSize = FONT_SIZE.sp
            )
        }

        Row {
            Button(
                onClick = {
                    // Actual strings need to be encoded
                    potionKey.let { color ->
                        val encodedColor = URLEncoder.encode(color, StandardCharsets.UTF_8.toString())
                        navController!!.navigate(Screens.ColorChecker.route + "?potionColor=$encodedColor")
                    }
                   // navController!!.navigate(Screens.ColorChecker.route.replace(oldValue = "{potionColor}", newValue = potionKey))
                },
                modifier = Modifier
                    .padding(bottom = 16.dp),
                border = BorderStroke(2.dp, Color.White),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(83, 12, 114), // Background color
                    contentColor = Color.White   // Text/icon color
                ),
            ) {
                Text(
                    text = "Validate", color = Color.White, style = TextStyle(
                        fontSize = 24.sp,
                        fontFamily = FontFamily(Font(resId = R.font.harry)),
                        color = Color.White
                    )
                )
            }
        }
    }
}


@SuppressLint("MissingPermission")
@Composable
fun TrackUserLocation(context: Context, onLocationUpdated: (LatLng) -> Unit) {
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    val locationCallback = rememberUpdatedState(
        object : com.google.android.gms.location.LocationCallback() {
            override fun onLocationResult(locationResult: com.google.android.gms.location.LocationResult) {
                val location = locationResult.lastLocation ?: return
                onLocationUpdated(LatLng(location.latitude, location.longitude))
            }
        }
    )

    DisposableEffect(Unit) {
        val locationRequest = com.google.android.gms.location.LocationRequest.Builder(
            com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY,
            1000L // Update interval (in milliseconds)
        ).build()

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback.value,
            Looper.getMainLooper()
        )

        onDispose {
            fusedLocationClient.removeLocationUpdates(locationCallback.value)
        }
    }
}

//Max distance of a Km: 0.01 degrees
fun generateRandomPlaces(numberPlaces: Int, location: LatLng): List<LatLng> {
    return List(numberPlaces) {
        val randomLatitude = location.latitude + Random.nextDouble(-0.01, 0.01)
        val randomLongitude = location.longitude + Random.nextDouble(-0.01, 0.01)
        LatLng(randomLatitude, randomLongitude)
    }
}

@Preview
@Composable
fun MapScreenPreview() {
    MapScreen(modifier = Modifier, null, null, null)
}

fun createNumberBitmap(context: Context, number: Int): Bitmap {
    val markerSize = 75
    val bitmap = Bitmap.createBitmap(markerSize, markerSize, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    // Paint for the circle
    val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        //color = android.graphics.Color.BLUE//argb(1, 102, 0, 204)// Circle color
        color = Color(0xFF9933FF).toArgb()
        style = Paint.Style.FILL
    }

    // Paint for the number
    val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = android.graphics.Color.WHITE
        textSize = 32f
        textAlign = Paint.Align.CENTER
        typeface = Typeface.DEFAULT_BOLD
    }

    // Draw the circle
    val radius = markerSize / 2f
    canvas.drawCircle(radius, radius, radius, circlePaint)

    // Draw the number in the center of the circle
    val textY = radius - (textPaint.descent() + textPaint.ascent()) / 2
    canvas.drawText(number.toString(), radius, textY, textPaint)

    return bitmap
}