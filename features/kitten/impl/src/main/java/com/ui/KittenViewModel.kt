package com.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.mapper.KittenItemMapper
import com.data.model.KittenItem
import com.sadri.core.data.entity.onFailed
import com.sadri.core.data.entity.onSuccess
import com.domain.interactor.LoadKittensUseCaseImpl
import com.sadri.api.LoadKittensUseCase
import com.sadri.core.navigation.Screen.Kitten.KittenArgs.CategoryId
import com.sadri.core.navigation.Screen.Kitten.KittenArgs.CategoryName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KittenViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  private val loadKittensUseCase: LoadKittensUseCase,
  private val kittenItemMapper: KittenItemMapper
) : ViewModel() {

  var state by mutableStateOf(KittenScreenState(savedStateHandle[CategoryName]!!))
    private set

  private val categoryId: String? = savedStateHandle[CategoryId]

  init {
    loadKittens()
  }

  private fun loadKittens() {
    requireNotNull(categoryId)
    viewModelScope.launch {
      loadKittensUseCase.load(categoryId.toLong())
        .onSuccess { kittenEntities ->
          state = state.copy(
            isLoading = false,
            isFailed = false,
            kittens = kittenItemMapper.mapToKittenItems(kittenEntities)
          )
        }
        .onFailed {
          state = state.copy(
            isLoading = false,
            isFailed = true
          )
        }
    }
  }

  fun retry() {
    state = state.copy(
      isLoading = true,
      isFailed = false
    )
    loadKittens()
  }

  fun loadMore() {
    requireNotNull(categoryId)
    state = state.copy(isLoadingMoreItems = true)
    viewModelScope.launch {
      loadKittensUseCase.load(categoryId.toLong())
        .onSuccess { kittenEntities ->
          val kittens = (state.kittens!! + kittenItemMapper.mapToKittenItems(kittenEntities))
            .toMutableList().distinctBy { it.id }
          state = state.copy(
            isLoadingMoreItems = false,
            kittens = kittens
          )
        }
        .onFailed {
          state = state.copy(isLoadingMoreItems = false)
        }
    }
  }
}

data class KittenScreenState(
  val categoryName: String,
  val isLoading: Boolean = true,
  val isLoadingMoreItems: Boolean = false,
  val isFailed: Boolean = false,
  val kittens: List<KittenItem>? = null
)