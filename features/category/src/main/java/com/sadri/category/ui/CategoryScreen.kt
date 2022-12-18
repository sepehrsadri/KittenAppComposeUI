package com.sadri.category.ui
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sadri.category.data.model.CategoryItem
import com.sadri.shared.common.compose.CardItem
import com.sadri.shared.common.compose.KittenItemsLoadingFailed
import com.sadri.shared.common.compose.KittenProgressItem
import com.sadri.shared.common.compose.ScaffoldWithTopBar

@Composable
fun CategoryScreen(
  modifier: Modifier = Modifier,
  categoryViewModel: CategoryViewModel = hiltViewModel(),
  onCategoryClicked: (String) -> Unit
) {
  val state = categoryViewModel.state
  when {
    state.isLoading -> {
      KittenProgressItem()
    }
    state.isFailed -> {
      KittenItemsLoadingFailed { categoryViewModel.retry() }
    }
    state.categories != null -> {
      CategoriesSection(
        modifier = modifier,
        categories = state.categories,
        onCategoryClicked = onCategoryClicked
      )
    }
  }

}

@Composable
fun CategoriesSection(
  modifier: Modifier,
  categories: List<CategoryItem>,
  onCategoryClicked: (String) -> Unit
) {
  ScaffoldWithTopBar(title = "Categories") {
    LazyColumn(
      modifier = modifier,
      contentPadding = PaddingValues(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      items(
        items = categories,
        key = { it.hashCode() }
      ) {
        it.title?.let { title ->
          CardItem(
            title = title,
            imageUrl = it.image,
          ) {
            onCategoryClicked(it.id.toString())
          }
        }
      }
    }
  }
}

inline fun <T> LazyListScope.items(
  items: List<T>,
  noinline key: ((item: T) -> Any)? = null,
  noinline contentType: (item: T) -> Any? = { null },
  crossinline itemContent: @Composable LazyItemScope.(item: T) -> Unit
) = items(
  count = items.size,
  key = if (key != null) { index: Int -> key(items[index]) } else null,
  contentType = { index: Int -> contentType(items[index]) }
) {
  itemContent(items[it])
}
