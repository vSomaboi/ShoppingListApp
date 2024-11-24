package hu.bme.aut.android.shoppinglist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hu.bme.aut.android.shoppinglist.feature.authentication.login.LoginScreen
import hu.bme.aut.android.shoppinglist.feature.authentication.register.RegisterScreen
import hu.bme.aut.android.shoppinglist.feature.contacts.ContactsScreen
import hu.bme.aut.android.shoppinglist.feature.createshoppinglist.CreateShoppingListScreen
import hu.bme.aut.android.shoppinglist.feature.main.MainScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginClick = {
                    navController.navigate(
                        route = Screen.Main.route
                    )
                },
                onRegisterClick = {
                    navController.navigate(
                        route = Screen.Register.route
                    )
                }
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterClick = {
                    navController.navigate(
                        route = Screen.Main.route
                    )
                }
            )
        }
        composable(Screen.Main.route) {
            MainScreen(
                createButtonClicked = {
                    navController.navigate(
                        route = Screen.CreateList.route
                    )
                },
                contactsButtonClicked = {
                    navController.navigate(
                        route = Screen.Contacts.route
                    )
                }
            )
        }
        composable(Screen.CreateList.route){
            CreateShoppingListScreen()
        }
        composable(Screen.Contacts.route) {
            ContactsScreen()
        }
    }
}