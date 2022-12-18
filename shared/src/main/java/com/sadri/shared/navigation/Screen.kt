package com.sadri.shared.navigation

import androidx.navigation.NavHostController
import com.sadri.shared.navigation.Screen.Kitten.KittenArgs.CategoryId
import com.sadri.shared.navigation.Screen.Kitten.KittenArgs.CategoryName

sealed class Screen(val route: String) {
  object Category : Screen("Category")

  object Kitten : Screen("Kittens/{${CategoryName}}/{${CategoryId}}") {
    object KittenArgs {
      const val CategoryId = "CategoryId"
      const val CategoryName = "CategoryName"
    }

    fun createRoute(categoryName: String, categoryId: String) = "Kittens/$categoryName/$categoryId"
  }
}

class Actions(navHostController: NavHostController) {
  val openKittenScreen: (String, String) -> Unit = { categoryName, categoryId ->
    navHostController.navigate(
      Screen.Kitten.createRoute(
        categoryName = categoryName,
        categoryId = categoryId
      )
    )
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