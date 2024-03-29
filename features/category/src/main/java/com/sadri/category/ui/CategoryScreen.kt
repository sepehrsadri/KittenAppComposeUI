package com.sadri.category.ui

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.sadri.category.R
import com.sadri.category.data.model.CategoryImage
import com.sadri.category.data.model.CategoryItem

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
      com.sadri.common.compose.KittenProgressItem()
    }

    state.isFailed -> {
      com.sadri.common.compose.KittenItemsLoadingFailed(
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
  com.sadri.common.compose.DrawerScaffoldWithTopBar(
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
            com.sadri.common.compose.CardItem(
              title = category.title,
              imageUrl = category.image.url,
            ) {
              onCategoryClicked(category.title, category.id.toString())
            }
          }

          is CategoryImage.Unknown -> {
            com.sadri.common.compose.CardItem(
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