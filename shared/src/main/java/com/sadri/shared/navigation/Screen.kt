package com.sadri.shared.navigation

import androidx.navigation.NavHostController
import com.sadri.shared.navigation.Screen.Kitten.KittenArgs.CategoryId

sealed class Screen(val route: String) {
  object Category : Screen("Category")

  object Kitten : Screen("Kitten/{$CategoryId}") {
    object KittenArgs {
      const val CategoryId = "CategoryId"
    }

    fun createRoute(categoryId: String) = "Kitten/$categoryId"
  }
}

class Actions(navHostController: NavHostController) {
  val openKittenScreen: (String) -> Unit = { categoryId ->
    navHostController.navigate(Screen.Kitten.createRoute(categoryId))
  }

  val navigateHome: () -> Unit = {
    navHostController.popBackStack(
      route = Screen.Category.route,
      inclusive = false
    )
  }

  val navigateBack: () -> Unit = {
    navHostController.navigateUp()
  }
}