package com.sadri.shared.domain.interactor

import com.sadri.shared.domain.repository.KittenRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.verifyBlocking

@RunWith(MockitoJUnitRunner::class)
class LoadCategoriesUseCaseTest {

  private val kittenRepository: KittenRepository = mock()

  private lateinit var loadCategoriesUseCase: LoadCategoriesUseCase

  @Before
  fun setUp() {
    MockitoAnnotations.openMocks(this)
    loadCategoriesUseCase = LoadCategoriesUseCase(kittenRepository)
  }

  @Test
  fun `should invoke repository load categories`() {
    runBlocking { loadCategoriesUseCase() }
    verifyBlocking(kittenRepository) { categories() }
  }
}