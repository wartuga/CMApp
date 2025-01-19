package com.cmapp.navigation

sealed class Screens(val route: String, val section: String) {
    object Data: Screens("data_screen", "Data")
    object LearnSpells: Screens("learn_spells_screen", "Spells")
    object PracticeSpells: Screens("practice_spells_screen", "Spells")
    object MovementSpells: Screens("movement_spells_screen/{spellKey}", "Spells")
    object LearnPotions: Screens("learn_potions_screen", "Potions")
    object PracticePotions: Screens("practice_potions_screen", "Potions")
    object MapPotions: Screens("map_potions_screen/{potionKey}", "Potions")
    object FriendsSocial: Screens("friends_social_screen", "Social")
    object SpellsSocial: Screens("spells_social_screen", "Social")
    object PotionsSocial: Screens("potions_social_screen", "Social")
    object WandProfile: Screens("wand_profile_screen", "Profile")
    object Register : Screens("register", "Home")
    object Login : Screens("login", "Home")
}