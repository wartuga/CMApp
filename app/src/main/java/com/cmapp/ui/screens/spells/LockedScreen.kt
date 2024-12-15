package com.cmapp.ui.screens.spells

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cmapp.R
import com.cmapp.ui.screens.utils.ScreenSkeleton
import com.cmapp.ui.screens.utils.SpellCard
import com.cmapp.ui.screens.utils.SwapButton

@Composable
fun LockedScreen(
    modifier: Modifier,
    navController: NavHostController?
) {
    ScreenSkeleton(
        navController = navController,
        composable = { LockedScreenContent(modifier) },
        modifier = modifier
    )
}

@Composable
fun LockedScreenContent(
    modifier: Modifier
) {
    val spells = listOf("Expelliarmus", "Lumos", "Alohomora", "Expecto Patronum", "Stupefy", "Obliviate")
    val spellname = "Amortentia"

    Column {
        Spacer(modifier = modifier.height(20.dp))
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Top
        ) {
            SwapButton(label = "Locked", modifier = modifier)
        }

        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.quests_bg),
                contentDescription = "Background",
                contentScale = ContentScale.FillWidth
            )
            Text(
                text = "Take this quizz to unlock",
                modifier = modifier
                    .padding(bottom = 45.dp)
            )
            Text(
                text = spellname,
                modifier = modifier
                    .align(Alignment.Center)
            )
            Image(
                painter = painterResource(id = R.drawable.timer),
                contentDescription = "Timer",
                modifier = modifier
                    .padding(top = 45.dp, end = 180.dp)
                    .size(24.dp)
            )
            Text(
                text = "3h",
                modifier = modifier.padding(top = 45.dp, end = 140.dp)
            )
        }

        spells.forEach { spell ->
            SpellCard(
                spellName = spell,
                button = {
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 40.dp),
                        enabled = false,
                        colors = ButtonDefaults.buttonColors(disabledContainerColor = Color.Gray),
                        border = BorderStroke(1.dp, Color.White)
                    ) {
                        Text(text = "Locked", color = Color.White, fontSize = 24.sp)
                    }
                },
                modifier = modifier
            )
        }
    }
}

@Preview
@Composable
fun PreviewLockedScreen() {
    LockedScreen(Modifier, null)
}