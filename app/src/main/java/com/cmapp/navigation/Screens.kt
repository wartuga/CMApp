package com.cmapp.navigation

sealed class Screens(val route: String, val section: String) {
    object Home: Screens("home_screen", "Home")
    object LearnSpells: Screens("learn_spells_screen", "Spells")
    object PracticeSpells: Screens("practice_spells_screen", "Spells")
    object MovementSpells: Screens("movement_spells_screen", "Spells")
    object LearnPotions: Screens("learn_potions_screen", "Potions")
    object PracticePotions: Screens("practice_potions_screen", "Potions")
    object MapPotions: Screens("map_potions_screen", "Potions")
    object FriendsSocial: Screens("friends_social_screen", "Social")
    object SpellsSocial: Screens("spells_social_screen", "Social")
    object PotionsSocial: Screens("potions_social_screen", "Social")
    object WandProfile: Screens("wand_profile_screen", "Profile")
}