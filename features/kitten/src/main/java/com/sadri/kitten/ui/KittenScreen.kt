package com.sadri.kitten.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sadri.kitten.data.model.KittenItem
import com.sadri.shared.common.compose.CardItem
import com.sadri.shared.common.compose.KittenItemsLoadingFailed
import com.sadri.shared.common.compose.KittenProgressItem
import com.sadri.shared.common.compose.ScaffoldWithTopBar
import com.sadri.shared.common.theme.LightKitten

@Composable
fun KittenScreen(
  modifier: Modifier = Modifier,
  kittenViewModel: KittenViewModel = hiltViewModel(),
) {
  val state = kittenViewModel.state
  when {
    state.isLoading -> {
      KittenProgressItem()
    }
    state.isFailed -> {
      KittenItemsLoadingFailed { kittenViewModel.retry() }
    }
    state.kittens != null -> {
      KittensSection(
        modifier = modifier,
        kittens = state.kittens,
        isLoadingMore = state.isLoadingMoreItems,
        onLoadMoreClick = { kittenViewModel.loadMore() }
      )
    }
  }

}

@Composable
fun KittensSection(
  modifier: Modifier = Modifier,
  kittens: List<KittenItem>,
  isLoadingMore: Boolean,
  onLoadMoreClick: () -> Unit
) {
  ScaffoldWithTopBar(title = "Kittens") {
    Column(
      modifier = modifier.verticalScroll(rememberScrollState()),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      LazyColumn(
        modifier = modifier.weight(1f),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        items(items = kittens, key = { it.hashCode() }) {
          CardItem(
            title = it.id,
            imageUrl = it.url,
          ) {
            // no-op
          }
        }
      }
      if (isLoadingMore) {
        KittenProgressItem()
      } else {
        TextButton(
          modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
          onClick = onLoadMoreClick,
          colors = ButtonDefaults.buttonColors(
            backgroundColor = LightKitten
          )
        ) {
          Text(
            text = "Load More",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.primary
          )
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