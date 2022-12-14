package com.sadri.shared.domain.interactor

import com.sadri.shared.domain.repository.KittenRepository
import javax.inject.Inject

class LoadCategoriesUseCase @Inject constructor(
  private val kittenRepository: KittenRepository
) {
  suspend operator fun invoke() = kittenRepository.categories()
}