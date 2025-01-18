package com.cmapp.ui.screens.social

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.cmapp.ui.screens.utils.PotionSpellCard
import com.cmapp.ui.screens.utils.RemoveButton
import com.cmapp.ui.screens.utils.ScreenSkeleton
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

    val scrollState = rememberScrollState()
    val unlockedPotions = listOf("AMORTENTIA", "FELIX FELICIS", "EDURUS")

    Column (modifier = modifier.verticalScroll(scrollState)){

        Spacer(modifier = modifier.height(24.dp))

        UserCard(
            username = "Dumbledory",
            composable = { RemoveButton(modifier) },
            modifier = modifier,
            picture = R.drawable.face, wand = R.drawable.wand_side

        )

        Spacer(modifier = modifier.height(24.dp))

        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "SPELLS",
                style = TextStyle(
                    fontSize = 36.sp,
                    fontFamily = FontFamily(Font(resId = R.font.harry)),
                    color = Color.Gray
                ),
                modifier = Modifier.clickable {
                    navController!!.navigate(Screens.SpellsSocial.route)
                },
            )
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = "POTIONS",
                style = TextStyle(
                    fontSize = 36.sp,
                    textDecoration = TextDecoration.Underline,
                    fontFamily = FontFamily(Font(resId = R.font.harry)),
                    color = Color.White
                )

            )
        }

        Spacer(modifier = modifier.height(16.dp))

        unlockedPotions.forEach { spell ->
            PotionSpellCard(
                name = spell,
                image = R.drawable.potion,
                description = "Heals magical ailments like poisoning or paralysis",
                buttonLabel = "Learn",
                modifier = modifier
            )
            Spacer(modifier = modifier.height(8.dp))
        }
    }
}

@Preview
@Composable
fun PreviewLearnedPotionsScreen() {
    LearnedPotionsScreen(Modifier, null, null, null)
}