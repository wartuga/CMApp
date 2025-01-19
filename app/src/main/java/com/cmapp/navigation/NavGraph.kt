package com.cmapp.navigation

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cmapp.model.domain.database.Spell
import com.cmapp.ui.DataPage
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
import com.google.gson.Gson
import kotlinx.serialization.json.Json

@Composable
fun NavGraph(
    navController: NavHostController,
    context: Context
    //viewModel: ViewModel
) {
    NavHost (
        navController = navController,
        startDestination = Screens.LearnSpells.route //Deve ser Login

        //Populate database
        //startDestination = Screens.Data.route
    ){
        composable(route = Screens.Data.route) {
            DataPage(navController = navController, context = context)
        }
        composable(route = Screens.Register.route) {
            RegisterScreen(navController = navController, context = context)
        }
        composable(route = Screens.Login.route) {
            LoginScreen(navController = navController, context = context)
        }
        composable(route = Screens.LearnSpells.route) {
            LearningScreen(navController = navController, context = context)
        }
        composable(route = Screens.PracticeSpells.route) {
            PracticingScreen(navController = navController, context = context)
        }

        composable(route = Screens.MovementSpells.route + "?spellKey={spellKey}") { navBackStack ->
            var spellKey: String = navBackStack.arguments?.getString("spellKey").toString()
            MovementScreen(navController = navController, context = context, spellKey = spellKey)
        }

        composable(route = Screens.LearnPotions.route) {
            com.cmapp.ui.screens.potions.LearningScreen(navController = navController, context = context)
        }
        composable(route = Screens.PracticePotions.route) {
            com.cmapp.ui.screens.potions.PracticingScreen(navController = navController, context = context)
        }
        composable(route = Screens.MapPotions.route + "?potionKey={potionKey}") { navBackStack ->
            val potionKey: String = navBackStack.arguments?.getString("potionKey").toString()
            MapScreen(navController = navController, context = context, potionKey = potionKey)
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