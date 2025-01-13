package com.cmapp.ui.screens.social

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cmapp.R
import com.cmapp.navigation.Screens
import com.cmapp.ui.screens.utils.RemoveButton
import com.cmapp.ui.screens.utils.ScreenSkeleton
import com.cmapp.ui.screens.utils.SpellCard
import com.cmapp.ui.screens.utils.SwapButton
import com.cmapp.ui.screens.utils.UserCard

@Composable
fun LearnedPotionsScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController?,
    context: Context?,
    friendId: Int?
) {
    ScreenSkeleton(
        navController = navController,
        composable = { LearnedPotionsScreenContent(modifier, navController) },
        modifier = modifier
    )
}

@Composable
fun LearnedPotionsScreenContent(modifier: Modifier, navController: NavHostController?) {

    val learnedSpells = listOf("Amortentia", "Felix Felicis", "Edurus")

    Column {

        Spacer(modifier = modifier.height(16.dp))

        UserCard(
            username = "Dumbledory",
            composable = { RemoveButton(modifier) },
            modifier = modifier
        )

        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Spells",
                style = TextStyle(
                    fontSize = 26.sp,
                    fontFamily = FontFamily(Font(resId = R.font.harry)),
                    color = Color.Gray
                ),
                modifier = Modifier.clickable {
                    navController!!.navigate(Screens.SpellsSocial.route)
                },
            )
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = "Potions",
                style = TextStyle(
                    fontSize = 26.sp,
                    textDecoration = TextDecoration.Underline,
                    fontFamily = FontFamily(Font(resId = R.font.harry)),
                    color = Color.White
                )

            )
        }

        learnedSpells.forEach { spell ->
            SpellCard(
                spellName = spell,
                button = {
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 40.dp),
                        border = BorderStroke(1.dp, Color.White)
                    ) {
                        Text(text = "Practice", color = Color.White, fontSize = 24.sp)
                    }
                },
                modifier = modifier
            )
        }
    }
}

@Preview
@Composable
fun PreviewLearnedPotionsScreen() {
    LearnedPotionsScreen(Modifier, null, null, null)
}