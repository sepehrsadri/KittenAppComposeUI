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
class LoadKittensUseCaseTest {

  private val kittenRepository: KittenRepository = mock()

  private lateinit var loadKittensUseCase: LoadKittensUseCase

  @Before
  fun setUp() {
    MockitoAnnotations.openMocks(this)
    loadKittensUseCase = LoadKittensUseCase(kittenRepository)
  }

  @Test
  fun `should invoke repository load kittens`() {
    val categoryId = 1L
    runBlocking { loadKittensUseCase(categoryId) }
    verifyBlocking(kittenRepository) { kittens(categoryId) }
  }
}