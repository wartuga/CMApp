package com.cmapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cmapp.ui.HomePage
import com.cmapp.ui.screens.FriendsListScreen
import com.cmapp.ui.screens.MapScreen
import com.cmapp.ui.screens.wand.MovementScreen
import com.cmapp.ui.screens.wand.SelectionScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    //viewModel: ViewModel
) {
    NavHost (
        navController = navController,
        startDestination = Screens.Home.route
    ){
        composable(route = Screens.Home.route) {
            HomePage(navController = navController)
        }
        composable(route = Screens.Spells.route) {
            MovementScreen(navController = navController)
        }
        composable(route = Screens.Potions.route) {
            MapScreen(navController = navController)
        }
        composable(route = Screens.Social.route) {
            FriendsListScreen(navController = navController)
        }
        composable(route = Screens.Profile.route) {
            SelectionScreen(navController = navController)
        }
    }
}