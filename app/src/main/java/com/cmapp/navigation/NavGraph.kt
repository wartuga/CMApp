package com.cmapp.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cmapp.ui.HomePage
import com.cmapp.ui.screens.social.FriendsListScreen
import com.cmapp.ui.screens.potions.MapScreen
import com.cmapp.ui.screens.profile.LoginScreen
import com.cmapp.ui.screens.profile.RegisterScreen
import com.cmapp.ui.screens.spells.MovementScreen
import com.cmapp.ui.screens.profile.WandSelectionScreen
import com.cmapp.ui.screens.social.LearnedPotionsScreen
import com.cmapp.ui.screens.social.LearnedSpellsScreen
import com.cmapp.ui.screens.spells.LearningScreen
import com.cmapp.ui.screens.spells.PracticingScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    context: Context
    //viewModel: ViewModel
) {
    NavHost (
        navController = navController,
        startDestination = Screens.Login.route
    ){
        composable(route = Screens.Home.route) {
            HomePage(navController = navController)
        }
        composable(route = Screens.Register.route) {
            RegisterScreen(navController = navController)
        }
        composable(route = Screens.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screens.LearnSpells.route) {
            LearningScreen(navController = navController, context = context)
        }
        composable(route = Screens.PracticeSpells.route) {
            PracticingScreen(navController = navController, context = context)
        }
        composable(route = Screens.MovementSpells.route + "?id={id}") { navBackStack ->
            val id: Int = navBackStack.arguments?.getString("id")?.toIntOrNull()?:0
            MovementScreen(navController = navController, context = context, spellId = id)
        }
        composable(route = Screens.LearnPotions.route) {
            com.cmapp.ui.screens.potions.LearningScreen(navController = navController, context = context)
        }
        composable(route = Screens.PracticePotions.route) {
            com.cmapp.ui.screens.potions.PracticingScreen(navController = navController, context = context)
        }
        composable(route = Screens.MapPotions.route + "?id={id}") { navBackStack ->
            val id: Int = navBackStack.arguments?.getString("id")?.toIntOrNull()?:0
            MapScreen(navController = navController, context = context, potionId = id)
        }
        composable(route = Screens.FriendsSocial.route) {
            FriendsListScreen(navController = navController, context = context)
        }
        composable(route = Screens.SpellsSocial.route + "?id={id}") { navBackStack ->
            val id: Int = navBackStack.arguments?.getString("id")?.toIntOrNull()?:0
            LearnedSpellsScreen(navController = navController, context = context, friendId = id)
        }
        composable(route = Screens.PotionsSocial.route + "?id={id}") { navBackStack ->
            val id: Int = navBackStack.arguments?.getString("id")?.toIntOrNull()?:0
            LearnedPotionsScreen(navController = navController, context = context, friendId = id)
        }
        composable(route = Screens.WandProfile.route) {
            WandSelectionScreen(navController = navController)
        }
    }
}