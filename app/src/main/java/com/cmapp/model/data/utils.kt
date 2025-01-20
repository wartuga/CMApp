package com.cmapp.model.data

import java.lang.Math.random
import java.util.Locale

const val MIN_USERNAME_LEN = 3
const val MAX_USERNAME_LEN = 20
const val MIN_PASSWORD_LEN = 8
const val MAX_PASSWORD_LEN = 30

fun isValidUsername(username: String) =
    username.matches(Regex("^(?=.*[a-zA-Z])[a-zA-Z0-9]*\$"))

fun isValidUsernameLength(username: String) =
    username.length in MIN_USERNAME_LEN..MAX_USERNAME_LEN

fun isValidPassword(password: String) =
    password.matches(Regex("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%^&*()\\-__+.]).*$"))

fun isValidPasswordLength(password: String) =
    password.length in MIN_PASSWORD_LEN..MAX_PASSWORD_LEN

fun getRandomPotionColor(): String {
    return "Blue"
}
fun toUpperCase(text: String): String{
    return text.uppercase(Locale.ROOT)
}

fun getRandomSpellColor(): String {
    return "Orange"
}

fun getRandomMovements(): List<String> {
    val movList = listOf("right","up-right","up","up-left","left")
    return List((10..20).random()) { _ -> movList.random() }
}