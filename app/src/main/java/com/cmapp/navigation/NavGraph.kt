package com.cmapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cmapp.ui.HomePage

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
            HomePage()
        }
        composable(route = Screens.Spells.route) {
            // TODO()
        }
        composable(route = Screens.Potions.route) {
            // TODO()
        }
        composable(route = Screens.Social.route) {
            // TODO()
        }
        composable(route = Screens.Profile.route) {
            // TODO()
        }
    }
}