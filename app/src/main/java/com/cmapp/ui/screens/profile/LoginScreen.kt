package com.cmapp.ui.screens.profile

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cmapp.model.data.DataBaseHelper.loginUser
import com.cmapp.model.data.StorageHelper.setUsername
import com.cmapp.navigation.Screens
import com.cmapp.ui.screens.utils.ScreenSkeleton

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    context: Context? = null,
    navController: NavHostController? = null
) {
    ScreenSkeleton(
        navController = navController,
        composable = {
            LoginScreenContent(modifier, context, navController)
        },
        modifier = modifier
    )
}

@Composable
fun LoginScreenContent(
    modifier: Modifier,
    context: Context?,
    navController: NavHostController?
){

    var username by remember{ mutableStateOf("") }
    var password by remember{ mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.size(100.dp))
        Row(horizontalArrangement = Arrangement.Center) {
            Text(text = "Login", color = Color.White, fontSize = 42.sp)
        }

        Spacer(modifier = Modifier.size(100.dp))

        Row(horizontalArrangement = Arrangement.Start) {
            Text(text = "Username", color = Color.White, fontSize = 24.sp)
        }
        Row {
            TextField(
                value = username,
                onValueChange = { newValue -> username = newValue },
                textStyle = TextStyle(fontSize = 20.sp)
            )
        }
        Spacer(modifier = Modifier.size(20.dp))
        Row(horizontalArrangement = Arrangement.Start) {
            Text(text = "Password", color = Color.White, fontSize = 24.sp)
        }
        Row {
            TextField(
                value = password,
                onValueChange = { newPassword -> password = newPassword },
                textStyle = TextStyle(fontSize = 20.sp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }

        Spacer(modifier = Modifier.size(100.dp))

        Row(horizontalArrangement = Arrangement.Center) {
            Button(
                onClick = {
                    loginUser(
                        username,
                        password,
                        onSuccess = {
                            setUsername(context!!, username)
                            navController?.navigate(Screens.WandProfile.route)
                        },

                        onError = { errorMessage ->
                            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                        }
                    )
                },
                content = { Text("Login") }
            )
        }
        Row(horizontalArrangement = Arrangement.Center) {
            Button(
                onClick = { navController?.navigate(Screens.Register.route) },
                content = { Text("New account") }
            )
        }
    }
}

@Preview
@Composable
fun LoginPreview() {
    LoginScreen(Modifier)
}