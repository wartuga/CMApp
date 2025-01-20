package com.cmapp.ui.screens.profile

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavHostController
import com.cmapp.model.data.DataBaseHelper.loginUser
import com.cmapp.model.data.StorageHelper.setUsername
import com.cmapp.navigation.Screens
import com.cmapp.ui.screens.utils.ScreenSkeleton

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    context: Context?,
    navController: NavHostController?
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

    Column {
        Text(text = "Login")
        Box {
            Column {
                Row {
                    Text(text = "Username:")
                    TextField(
                        value = username,
                        onValueChange = { newValue -> username = newValue }
                    )
                }
                Row {
                    Text(text = "Password:")
                    TextField(
                        value = password,
                        onValueChange = { newPassword -> password = newPassword },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )
                }
                Row {
                    Button(
                        onClick = {
                            loginUser(
                                username,
                                password,
                                onSuccess = {
                                    setUsername(context!!, username)
                                    navController?.navigate(Screens.LearnSpells.route)
                                            },
                                onError = { /* apresentar toast */ }
                            )
                        },
                        content = { Text("Login") }
                    )
                }

                Row {
                    Button(
                        onClick = { navController?.navigate(Screens.Register.route) },
                        content = { Text("New account") }
                    )
                }
            }
        }
    }
}