package com.sadri.kitten.ui

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
import com.sadri.kitten.data.model.KittenItem
import com.sadri.shared.common.compose.CardItem
import com.sadri.shared.common.compose.KittenItemsLoadingFailed
import com.sadri.shared.common.compose.KittenProgressItem
import com.sadri.shared.common.compose.ScaffoldWithTopBar

@Composable
fun KittenScreen(
  kittenViewModel: KittenViewModel = hiltViewModel()
) {
  val state = kittenViewModel.state
  when {
    state.isLoading -> {
      KittenProgressItem()
    }
    state.isFailed -> {
      KittenItemsLoadingFailed()
    }
    state.kittens != null -> {
      KittensSection(state.kittens)
    }
  }

}

@Composable
fun KittensSection(
  categories: List<KittenItem>
) {
  ScaffoldWithTopBar(title = "Kittens") {
    LazyColumn(
      modifier = Modifier
        .fillMaxSize(),
      contentPadding = PaddingValues(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      items(
        items = categories,
        key = { it.hashCode() }
      ) {
        CardItem(
          title = it.id,
          imageUrl = it.url,
        ) {
          // no-op
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