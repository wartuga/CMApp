package com.cmapp.ui.screens.spells

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cmapp.ui.screens.utils.ScreenSkeleton
import com.cmapp.ui.screens.utils.SpellCard
import com.cmapp.ui.screens.utils.SwapButton

@Composable
fun UnlockedScreen(
    modifier: Modifier
) {
    ScreenSkeleton(
        composable = { UnlockedScreenContent(modifier) },
        modifier = modifier
    )
}

@Composable
fun UnlockedScreenContent(
    modifier: Modifier
) {
    val unlockedSpells = listOf("Expelliarmus", "Lumos", "Alohomora", "Expecto Patronum", "Stupefy", "Obliviate")

    Column {
        Spacer(modifier = modifier.height(20.dp))
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SwapButton(label = "Unlocked", modifier = modifier)
        }

        unlockedSpells.forEach { spell ->
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
fun PreviewUnlockedScreen() {
    UnlockedScreen(Modifier)
}