package com.cmapp.ui.screens.potions

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cmapp.navigation.Screens
import com.cmapp.ui.screens.utils.ScreenSkeleton
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import kotlin.random.Random

const val TITLE_SIZE = 28
const val FONT_SIZE = 16
const val MAP_SIZE = 330
const val PADDING = 5

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController?,
    context: Context?,
    potionId: Int?
) {
    ScreenSkeleton(
        navController = navController,
        composable = {
            CheckAndRequestLocationPermission(context = context ?: return@ScreenSkeleton) {
                MapScreenContent(modifier, navController)
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
private fun MapScreenContent(modifier: Modifier, navController: NavHostController?) {
    val ingredients = listOf("Ingredient 1", "Ingredient 2", "Ingredient 3")
    val effect = "Effect"
    Column(

        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = modifier.padding(PADDING.dp)) {
            Text(
                text = "Feitiço",
                color = Color.White,
                fontSize = TITLE_SIZE.sp
            )
        }
        Row(modifier = modifier.padding(PADDING.dp)) {
            Text(
                text = "Effect: $effect",
                color = Color.White,
                fontSize = FONT_SIZE.sp
            )
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
        Row(modifier = modifier.padding(PADDING.dp)) {
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

            // Generate random places once the user location is available
            val places = remember(userLocation) {
                userLocation?.let { generateRandomPlaces(5, it) } ?: emptyList()
            }

            GoogleMap(
                modifier = Modifier.height(450.dp),//.fillMaxSize(),
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
                places.forEach { place ->
                    Marker(
                        position = place,
                        title = "Ingredient",
                        snippet = "An ingredient description"
                    )
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
                onClick = {navController!!.navigate(Screens.ColorChecker.route)},
                modifier = Modifier
                    .padding(bottom = 40.dp),
                border = BorderStroke(1.dp, Color.White)
            ) {
                Text(text = "Validate", color = Color.White, fontSize = 24.sp)
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

// Distance of a Km: 0.01 degrees
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
    MapScreen(modifier = Modifier, navController = null, context = null, potionId = null)
}