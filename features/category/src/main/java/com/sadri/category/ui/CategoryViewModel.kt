package com.sadri.category.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadri.category.data.mapper.CategoryItemMapper
import com.sadri.category.data.model.CategoryImage
import com.sadri.category.data.model.CategoryItem
import com.sadri.shared.data.entity.onFailed
import com.sadri.shared.data.entity.onSuccess
import com.sadri.shared.domain.executor.DispatcherProvider
import com.sadri.shared.domain.interactor.LoadCategoriesUseCase
import com.sadri.shared.domain.interactor.LoadKittensUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
  private val loadCategoriesUseCase: LoadCategoriesUseCase,
  private val loadKittensUseCase: LoadKittensUseCase,
  private val categoryItemMapper: CategoryItemMapper,
  private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

  var state by mutableStateOf(CategoryScreenState())
    private set

  init {
    loadCategories()
  }

  fun retry() {
    state = state.copy(
      isLoading = true,
      isFailed = false
    )
    loadCategories()
  }

  private fun loadCategories() {
    viewModelScope.launch {
      loadCategoriesUseCase()
        .onSuccess { categories ->
          state = state.copy(
            isLoading = false,
            isFailed = false,
            categories = categoryItemMapper.mapToCategoryItems(categories)
          )
          loadCategoryPictures()
        }
        .onFailed {
          state = state.copy(
            isLoading = false,
            isFailed = true
          )
        }
    }
  }

  // TODO: Save result and load kittens from there
  private fun loadCategoryPictures() {
    viewModelScope.launch {
      withContext(dispatcherProvider.default) {
        val categories = state.categories!!.toMutableList()
        state.categories!!.forEachIndexed { index, category ->
          loadKittensUseCase(category.id)
            .onSuccess { kittens ->
              val imageUrl = kittens.first().url
              categories[index] = categories[index].copy(image = CategoryImage.Url(imageUrl))
            }
        }
        state = state.copy(categories = categories)
      }
    }
  }
}

data class CategoryScreenState(
  val isLoading: Boolean = true,
  val isFailed: Boolean = false,
  val categories: List<CategoryItem>? = null
)
