package com.sadri.kitten.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadri.kitten.data.mapper.KittenItemMapper
import com.sadri.kitten.data.model.KittenItem
import com.sadri.shared.data.entity.onFailed
import com.sadri.shared.data.entity.onSuccess
import com.sadri.shared.domain.interactor.LoadKittensUseCase
import com.sadri.shared.navigation.Screen.Kitten.KittenArgs.CategoryId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KittenViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  private val loadKittensUseCase: LoadKittensUseCase,
  private val kittenItemMapper: KittenItemMapper
) : ViewModel() {

  var state by mutableStateOf(KittenScreenState())
    private set

  private val categoryId: String? = savedStateHandle[CategoryId]

  init {
    requireNotNull(categoryId)
    loadKittens(categoryId)
  }

  private fun loadKittens(categoryId: String) {
    viewModelScope.launch {
      loadKittensUseCase(categoryId.toLong())
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
}

data class KittenScreenState(
  val isLoading: Boolean = true,
  val isFailed: Boolean = false,
  val kittens: List<KittenItem>? = null
)