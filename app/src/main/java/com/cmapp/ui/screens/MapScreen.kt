package com.cmapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cmapp.R
import com.cmapp.ui.screens.utils.ScreenSkeleton

const val TITLE_SIZE = 28
const val FONT_SIZE = 16
const val MAP_SIZE = 330
const val PADDING = 5

@Composable
fun MapScreen(modifier: Modifier) {
    ScreenSkeleton(
        composable = {
            MapScreenContent(modifier)
        },
        modifier
    )
}

@Composable
private fun MapScreenContent(modifier: Modifier) {
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
            Image(
                painter = painterResource(id = R.drawable.map),
                contentDescription = "Map",
                modifier = modifier.size(MAP_SIZE.dp)
            )
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

@Preview
@Composable
fun MapScreenPreview() {
    MapScreen(modifier = Modifier)
}