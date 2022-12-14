package com.sadri.category.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadri.category.data.mapper.CategoryItemMapper
import com.sadri.category.data.model.CategoryItem
import com.sadri.shared.data.entity.onFailed
import com.sadri.shared.data.entity.onSuccess
import com.sadri.shared.domain.interactor.LoadCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
  private val loadCategoriesUseCase: LoadCategoriesUseCase,
  private val categoryItemMapper: CategoryItemMapper
) : ViewModel() {

  var state by mutableStateOf(CategoryScreenState())
    private set

  init {
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

data class CategoryScreenState(
  val isLoading: Boolean = true,
  val isFailed: Boolean = false,
  val categories: List<CategoryItem>? = null
)
