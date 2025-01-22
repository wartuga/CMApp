package com.cmapp.ui.screens.potions

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.cmapp.model.data.DataBaseHelper.getLearnedPotions
import com.cmapp.model.data.StorageHelper.getUsername
import com.cmapp.model.data.toUpperCase
import com.cmapp.model.domain.database.Potion
import com.cmapp.navigation.Screens
import com.cmapp.ui.screens.utils.PotionSpellCard
import com.cmapp.ui.screens.utils.ScreenSkeleton

@Composable
fun PracticingScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController?,
    context: Context?
) {
    ScreenSkeleton(
        navController = navController,
        composable = { PracticingScreenContent(modifier, navController, context) },
        modifier = modifier
    )
}

@Composable
fun PracticingScreenContent(
    modifier: Modifier,
    navController: NavHostController?,
    context: Context?
) {
    var potions by remember { mutableStateOf<List<Potion>>(mutableListOf()) }
    getLearnedPotions(getUsername(context!!)){ potionsDb -> potions = potionsDb }

    //USER INFO

    val scrollState = rememberScrollState()

    Column (modifier = Modifier.verticalScroll(scrollState)){
        Spacer(modifier = modifier.height(24.dp))
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "ALL",
                style = TextStyle(
                    fontSize = 36.sp,
                    fontFamily = FontFamily(Font(resId = R.font.harry)),
                    color = Color.Gray
                ),
                modifier = Modifier.clickable {
                    navController!!.navigate(Screens.LearnPotions.route)
                },
            )
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = "LEARNED",
                style = TextStyle(
                    fontSize = 36.sp,
                    textDecoration = TextDecoration.Underline,
                    fontFamily = FontFamily(Font(resId = R.font.harry)),
                    color = Color.White
                )

            )
        }

        Spacer(modifier = modifier.height(16.dp))

        potions.forEach { potion ->
            potion.name?.let {
                PotionSpellCard(
                    name = toUpperCase(potion.name!!),
                    description = potion.description!!,
                    image = potion.image!!,
                    buttonLabel = "Practice",
                    onButtonClick = {
                        navController!!.navigate(
                            Screens.MapPotions.route.replace(
                                oldValue = "{potionKey}",
                                newValue = potion.key!!
                            )
                        )
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewPracticingScreen() {
    PracticingScreen(Modifier, null, null)
}