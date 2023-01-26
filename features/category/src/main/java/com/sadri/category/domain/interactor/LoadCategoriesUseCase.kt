package com.sadri.category.domain.interactor

import com.sadri.category.domain.repository.CategoryRepository
import javax.inject.Inject

class LoadCategoriesUseCase @Inject constructor(
  private val categoryRepository: CategoryRepository
) {
  suspend operator fun invoke() = categoryRepository.categories()
}