package com.domain.interactor

import com.domain.repository.KittenRepository
import com.sadri.api.LoadKittensUseCase
import javax.inject.Inject

class LoadKittensUseCaseImpl @Inject constructor(
  private val kittenRepository: KittenRepository
) : LoadKittensUseCase {
  override suspend fun load(categoryId: Long) = kittenRepository.kittens(categoryId)
}