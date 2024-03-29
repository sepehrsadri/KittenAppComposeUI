package com.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.sadri.common.compose.KittenItemsLoadingFailed
import com.sadri.common.compose.KittenProgressItem
import com.data.model.KittenItem

@Composable
fun KittenScreen(
  modifier: Modifier = Modifier,
  kittenViewModel: KittenViewModel = hiltViewModel(),
  darkTheme: Boolean,
  onThemeChanged: (Boolean) -> Unit
) {
  val state = kittenViewModel.state
  when {
    state.isLoading -> {
      KittenProgressItem()
    }

    state.isFailed -> {
      KittenItemsLoadingFailed(
        darkTheme = darkTheme,
        onThemeChanged = onThemeChanged
      ) { kittenViewModel.retry() }
    }

    state.kittens != null -> {
      KittensSection(
        modifier = modifier,
        category = state.categoryName,
        kittens = state.kittens,
        isLoadingMore = state.isLoadingMoreItems,
        onLoadMoreClick = { kittenViewModel.loadMore() },
        darkTheme = darkTheme,
        onThemeChanged = onThemeChanged
      )
    }
  }

}

@Composable
fun KittensSection(
  modifier: Modifier = Modifier,
  category: String,
  kittens: List<KittenItem>,
  isLoadingMore: Boolean,
  onLoadMoreClick: () -> Unit,
  darkTheme: Boolean,
  onThemeChanged: (Boolean) -> Unit
) {
  com.sadri.common.compose.DrawerScaffoldWithTopBar(
    modifier = modifier,
    title = "${category.capitalize()} Kittens",
    darkTheme = darkTheme,
    onThemeChanged = onThemeChanged
  ) {
    Column(
      modifier = modifier.verticalScroll(rememberScrollState()),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      LazyVerticalGrid(
        modifier = modifier.weight(1f),
        columns = GridCells.Fixed(2)
      ) {
        items(items = kittens, key = { it.hashCode() }) {
          com.sadri.common.compose.CardItem(imageUrl = it.url) {
            // no-op
          }
        }
      }
      if (isLoadingMore) {
        com.sadri.common.compose.KittenProgressItem()
      } else {
        TextButton(
          modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
          onClick = onLoadMoreClick,
          colors = ButtonDefaults.buttonColors(
            backgroundColor = com.sadri.common.theme.LightKitten
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