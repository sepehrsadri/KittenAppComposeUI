package com.sadri.category.ui

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.sadri.category.data.model.CategoryImage
import com.sadri.category.data.model.CategoryItem
import com.sadri.shared.R
import com.sadri.shared.common.compose.CardItem
import com.sadri.shared.common.compose.DrawerScaffoldWithTopBar
import com.sadri.shared.common.compose.KittenItemsLoadingFailed
import com.sadri.shared.common.compose.KittenProgressItem

@Composable
fun CategoryScreen(
  modifier: Modifier = Modifier,
  categoryViewModel: CategoryViewModel = hiltViewModel(),
  onCategoryClicked: (String, String) -> Unit,
  darkTheme: Boolean,
  onThemeChanged: (Boolean) -> Unit
) {
  val state = categoryViewModel.state
  when {
    state.isLoading -> {
      KittenProgressItem()
    }

    state.isFailed -> {
      KittenItemsLoadingFailed(
        darkTheme = darkTheme,
        onThemeChanged = onThemeChanged
      ) { categoryViewModel.retry() }
    }

    state.categories != null -> {
      CategoriesSection(
        modifier = modifier,
        categories = state.categories,
        onCategoryClicked = onCategoryClicked,
        darkTheme = darkTheme,
        onThemeChanged = onThemeChanged
      )
    }
  }

}

@Composable
fun CategoriesSection(
  modifier: Modifier,
  categories: List<CategoryItem>,
  onCategoryClicked: (String, String) -> Unit,
  darkTheme: Boolean,
  onThemeChanged: (Boolean) -> Unit
) {
  DrawerScaffoldWithTopBar(
    modifier = modifier,
    title = "Categories",
    darkTheme = darkTheme,
    onThemeChanged = onThemeChanged
  ) {
    LazyVerticalGrid(
      modifier = modifier,
      columns = GridCells.Fixed(2)
    ) {
      items(
        items = categories,
        key = { it.hashCode() }
      ) { category ->
        when (category.image) {
          is CategoryImage.Url -> {
            CardItem(
              title = category.title,
              imageUrl = category.image.url,
            ) {
              onCategoryClicked(category.title, category.id.toString())
            }
          }

          is CategoryImage.Unknown -> {
            CardItem(
              title = category.title,
              imageResource = R.drawable.unknown_kitten,
            ) {
              onCategoryClicked(category.title, category.id.toString())
            }
          }
        }
      }
    }
  }
}