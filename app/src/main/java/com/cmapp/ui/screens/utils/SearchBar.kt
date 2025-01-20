package com.cmapp.ui.screens.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.cmapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .border(BorderStroke(3.dp, Color.White), shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp))
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, // Aligns the icon and text field vertically
        ) {
            // Icon
            Icon(
                painter = painterResource(id = R.drawable.magnifying_glass), // Replace with your icon
                contentDescription = "Search icon",
                tint = Color.White,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp).size(30.dp) // Adds some space between the icon and the text field
            )

            // Text Input (TextField)
            TextField(
                value = TextFieldValue(""), // Replace with your TextField value
                onValueChange = { newText -> /* Handle text change */ },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent, // Makes background transparent
                    focusedIndicatorColor = Color.Transparent, // Hides the indicator
                    unfocusedIndicatorColor = Color.Transparent // Hides the indicator when unfocused
                )
            )
        }
    }
}

@Composable
@Preview
fun SearchBarPreview(){
    SearchBar()
}