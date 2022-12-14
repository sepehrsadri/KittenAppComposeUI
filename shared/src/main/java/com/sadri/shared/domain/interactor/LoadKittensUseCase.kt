package com.sadri.shared.domain.interactor

import com.sadri.shared.domain.repository.KittenRepository
import javax.inject.Inject

class LoadKittensUseCase @Inject constructor(
  private val kittenRepository: KittenRepository
) {
  suspend operator fun invoke(categoryId: Long) = kittenRepository.kittens(categoryId)
}