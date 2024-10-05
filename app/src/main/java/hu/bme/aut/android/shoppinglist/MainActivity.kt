package hu.bme.aut.android.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.shoppinglist.navigation.NavGraph
import hu.bme.aut.android.shoppinglist.ui.theme.ShoppingListTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingListTheme {
                NavGraph()
            }
        }
    }
}