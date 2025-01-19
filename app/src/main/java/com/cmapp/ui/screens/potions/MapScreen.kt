package com.cmapp.ui.screens.potions

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cmapp.model.data.DataBaseHelper.getPotion
import com.cmapp.model.data.DataBaseHelper.getSpell
import com.cmapp.model.domain.database.Potion
import com.cmapp.model.domain.database.Spell
import com.cmapp.ui.screens.utils.ScreenSkeleton
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
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
fun MapScreen(modifier: Modifier = Modifier, navController: NavHostController?, context: Context?, potionKey: String?) {
    ScreenSkeleton(
        navController = navController,
        composable = {
            MapScreenContent(modifier, context, potionKey!!)
        },
        modifier
    )
}

@Composable
private fun MapScreenContent(modifier: Modifier, context: Context?, potionKey: String) {

    var potion by remember { mutableStateOf<Potion>(Potion()) } //Ir buscar a pocao a base de dados
    getPotion(potionKey){ potionDb -> potion = potionDb }

    var ingredients = listOf("")
    potion.ingredients?.let{
        ingredients = listOf(it)
        if(it.contains(","))
            ingredients = it.split(",")
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
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
//            Image(
//                painter = painterResource(id = R.drawable.map),
//                contentDescription = "Map",
//                modifier = modifier.size(MAP_SIZE.dp)
//            )

            //https://mapsplatform.google.com/resources/blog/compose-maps-sdk-android-now-available/
            var uiSettings by remember { mutableStateOf(MapUiSettings()) }
            var properties by remember {
                mutableStateOf(MapProperties(mapType = MapType.SATELLITE))
            }
            val fculLoc = LatLng(38.756544413269, -9.155370717573753)


            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(fculLoc, 15f)
            }
            val places = remember {
                generateRandomPlaces(5, fculLoc)
            }
            //38.757238, -9.155648
            //Max distance of a Km: 0.01 degrees
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                properties = properties,
                uiSettings = uiSettings,
                cameraPositionState = cameraPositionState
            ) {
                Marker(
                    position = fculLoc,
                    title = "Fcul",
                    snippet = "Marker in Fcul",
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
               )
                for (place in places)
                    Marker(
                        position = place,
                        title = "Ingredient",
                        snippet = "Marker in Ingredient"
                    )
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
        Row(modifier = modifier.padding(PADDING.dp)) {
            Button(
                onClick = {},
                content = {
                    Text(
                        text = "Validate",
                        fontSize = FONT_SIZE.sp
                    )
                }
            )
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