package com.sadri.kitten

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sadri.category.ui.CategoryScreen
import com.sadri.shared.navigation.Actions
import com.sadri.shared.navigation.Screen
import com.sadri.kitten.ui.KittenScreen
import com.sadri.shared.common.theme.KittenTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      KittenApp()
    }
  }
}

@Composable
fun KittenApp() {
  val navController = rememberNavController()
  val actions = remember(navController) { Actions(navController) }
  KittenTheme {
    Surface(
      modifier = Modifier.fillMaxSize(),
      color = MaterialTheme.colors.background
    ) {
      NavHost(
        navController = navController,
        startDestination = Screen.Category.route
      ) {
        composable(Screen.Category.route) {
          CategoryScreen(onCategoryClicked = actions.openKittenScreen)
        }
        composable(Screen.Kitten.route) {
          KittenScreen(modifier = Modifier.fillMaxSize())
        }
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  KittenTheme {
    CategoryScreen(
      onCategoryClicked = { println("On Feed $it Clicked") }
    )
  }
}
