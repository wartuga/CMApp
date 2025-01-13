package com.cmapp.ui.screens.spells

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
import com.cmapp.ui.screens.utils.ScreenSkeleton
import com.cmapp.ui.screens.utils.SpellCard
import com.cmapp.ui.screens.utils.SwapButton

@Composable
fun LearningScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController?,
    context: Context?
) {
    ScreenSkeleton(
        navController = navController,
        composable = { LearningScreenContent(modifier, navController) },
        modifier = modifier
    )
}

@Composable
fun LearningScreenContent(
    modifier: Modifier,
    navController: NavHostController?
) {
    val unlockedSpells = listOf("Expelliarmus", "Lumos", "Alohomora", "Expecto Patronum", "Stupefy", "Obliviate")

    Column {
        Spacer(modifier = modifier.height(20.dp))
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Learning",
                style = TextStyle(
                    fontSize = 26.sp,
                    textDecoration = TextDecoration.Underline,
                    fontFamily = FontFamily(Font(resId = R.font.harry)),
                    color = Color.White
                )
            )
            Text(
                text = "Practicing",
                style = TextStyle(
                    fontSize = 26.sp,
                    fontFamily = FontFamily(Font(resId = R.font.harry)),
                    color = Color.Gray
                ),
                modifier = Modifier.padding(start = 10.dp).clickable {
                    navController!!.navigate(Screens.PracticeSpells.route)
                },
            )
        }

        unlockedSpells.forEach { spell ->
            SpellCard(
                spellName = spell,
                button = {
                    Button(
                        onClick = {navController!!.navigate(Screens.MovementSpells.route)},
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 40.dp),
                        border = BorderStroke(1.dp, Color.White)
                    ) {
                        Text(text = "Learn", color = Color.White, fontSize = 24.sp)
                    }
                },
                modifier = modifier
            )
        }
    }
}

@Preview
@Composable
fun PreviewLearningScreen() {
    LearningScreen(Modifier, null, null)
}