package com.cmapp.ui.screens.wand

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cmapp.R
import com.cmapp.ui.screens.utils.ScreenSkeleton
import kotlin.random.Random
import kotlin.random.nextInt

@Composable
fun MovementScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController?
) {
    ScreenSkeleton(
        navController = navController,
        composable = { MovementScreenContent(modifier) },
        modifier = modifier
    )
}

@Composable
private fun MovementScreenContent(modifier: Modifier) {
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
            val mov1 = Random.nextInt(1..5)
            val mov2 = Random.nextInt(1..5)
            val mov3 = Random.nextInt(1..5)
            listOf(mov1, mov2, mov3).forEach {
                Image(
                    painter = painterResource(
                        id = when (it) {
                            1 -> R.drawable.arrow_left
                            2 -> R.drawable.arrow_up_left
                            3 -> R.drawable.arrow_up
                            4 -> R.drawable.arrow_up_right
                            else -> R.drawable.arrow_right
                        }
                    ),
                    contentDescription = "Movimento $it",
                    modifier = Modifier.size(80.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
        }
        Row(modifier = modifier.padding(16.dp)) {
            Text(
                text = "Varinha",
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
fun PreviewWandScreen() {
    MovementScreen(
        modifier = Modifier,
        null
    )
}