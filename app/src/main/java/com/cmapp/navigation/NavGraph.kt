package com.cmapp.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cmapp.model.data.StorageHelper.getUsername
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
import com.cmapp.ui.screens.potions.ColorCheckerScreen
import com.google.gson.Gson
import kotlinx.serialization.json.Json

@Composable
fun NavGraph(
    navController: NavHostController,
    context: Context
    //viewModel: ViewModel
) {
    var startDestination = Screens.Login.route
    if(getUsername(context).isNotEmpty()){
        startDestination = Screens.LearnSpells.route
    }

    NavHost (
        navController = navController,
        startDestination = startDestination

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
        composable(route = Screens.SpellsSocial.route + "?friendUsername={friendUsername}") { navBackStack ->
            val friendUsername: String = navBackStack.arguments?.getString("friendUsername").toString()
            LearnedSpellsScreen(navController = navController, context = context, friendUsername = friendUsername)
        }
        composable(route = Screens.PotionsSocial.route + "?friendUsername={friendUsername}") { navBackStack ->
            val friendUsername: String = navBackStack.arguments?.getString("friendUsername").toString()
            LearnedPotionsScreen(navController = navController, context = context, friendUsername = friendUsername)
        }
        composable(route = Screens.WandProfile.route) {
            WandSelectionScreen(navController = navController, context = context)
        }
        composable(route = Screens.ColorChecker.route + "?potionColor={potionColor}") { navBackStack ->
            val potionColor: String = navBackStack.arguments?.getString("potionColor").toString()
            ColorCheckerScreen(navController = navController, context = context, potionColor = potionColor)
        }
    }
}