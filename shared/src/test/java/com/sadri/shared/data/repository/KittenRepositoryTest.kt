package com.sadri.shared.data.repository

import com.sadri.shared.data.datasource.KittenDataSource
import com.sadri.shared.data.mapper.DataErrorMapper
import com.sadri.shared.domain.repository.KittenRepository
import com.sadri.test_utils.TestDispatcherProvider
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
class KittenRepositoryTest {

  private val kittenDataSource: KittenDataSource = mock()

  private lateinit var kittenRepository: KittenRepository

  private val dataErrorMapper = DataErrorMapper()

  private val testDispatcherProvider = TestDispatcherProvider

  @Before
  fun setUp() {
    MockitoAnnotations.openMocks(this)
    kittenRepository = KittenRepositoryImpl(
      dispatcherProvider = testDispatcherProvider,
      dataErrorMapper = dataErrorMapper,
      kittenDataSource = kittenDataSource
    )
  }

  @Test
  fun `should return categories from dataSource`() {
    runBlocking { kittenRepository.categories() }
    verifyBlocking(kittenDataSource) { categories() }
  }

  @Test
  fun `should return kittens from dataSource`() {
    val categoryId = 1L
    runBlocking { kittenRepository.kittens(categoryId) }
    verifyBlocking(kittenDataSource) { kittens(categoryId) }
  }
}