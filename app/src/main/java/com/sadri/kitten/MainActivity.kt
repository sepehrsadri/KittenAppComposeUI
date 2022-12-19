package com.sadri.kitten

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
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
import com.sadri.kitten.ui.KittenScreen
import com.sadri.shared.common.theme.KittenTheme
import com.sadri.shared.navigation.Actions
import com.sadri.shared.navigation.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  private val mainViewModel: MainViewModel by viewModels()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val darkThemeState = mainViewModel.darkThemeState
      KittenApp(darkThemeState.darkTheme) {
        mainViewModel.onDarkThemeChanged(it)
      }
    }
  }
}

@Composable
fun KittenApp(
  darkTheme: Boolean,
  onThemeChanged: (Boolean) -> Unit
) {
  val navController = rememberNavController()
  val actions = remember(navController) { Actions(navController) }
  KittenTheme(darkTheme = darkTheme) {
    Surface(
      modifier = Modifier.fillMaxSize(),
      color = MaterialTheme.colors.background
    ) {
      NavHost(
        navController = navController,
        startDestination = Screen.Category.route
      ) {
        composable(Screen.Category.route) {
          CategoryScreen(
            modifier = Modifier.fillMaxSize(),
            onCategoryClicked = actions.openKittenScreen,
            darkTheme = darkTheme,
            onThemeChanged = onThemeChanged
          )
        }
        composable(Screen.Kitten.route) {
          KittenScreen(
            modifier = Modifier.fillMaxSize(),
            darkTheme = darkTheme,
            onThemeChanged = onThemeChanged
          )
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
      onCategoryClicked = { categoryName, categoryId ->
        println("On Category Id $categoryId with name $categoryName Clicked")
      },
      darkTheme = isSystemInDarkTheme(),
      onThemeChanged = {}
    )
  }
}
