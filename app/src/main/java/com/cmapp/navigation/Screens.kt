package com.cmapp.navigation

sealed class Screens(val route: String) {
    object Home: Screens("home_screen")
    object Spells: Screens("spell_screen")
    object Potions: Screens("potions_screen")
    object Social: Screens("social_screen")
    object Profile: Screens("profile_screen")
}