package com.sadri.category.ui

import com.sadri.category.data.mapper.CategoryItemMapper
import com.sadri.core.data.entity.CategoryEntity
import com.sadri.core.data.entity.KittenEntity
import com.sadri.core.data.entity.Result
import com.sadri.shared.domain.interactor.LoadCategoriesUseCase
import com.sadri.kitten.domain.interactor.LoadKittensUseCase
import com.sadri.test_utils.CoroutineTestRule
import com.sadri.test_utils.TestDispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub
import org.mockito.kotlin.times
import org.mockito.kotlin.verifyBlocking

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class CategoryViewModelBehaviouralTest {

  @get:Rule
  val coroutineTestRule = CoroutineTestRule()

  private val loadKittensUseCase: com.sadri.kitten.domain.interactor.LoadKittensUseCase = mock()

  private val loadCategoriesUseCase: LoadCategoriesUseCase = mock()

  private val dispatcherProvider = TestDispatcherProvider

  private val categoryItemMapper = CategoryItemMapper()

  private lateinit var categoryViewModel: CategoryViewModel

  @Test
  fun `given invoke viewModel then should invoke loadCategoriesUseCase`() = runTest {
    // given
    val categories = listOf(
      com.sadri.core.data.entity.CategoryEntity(id = 1, name = "box"),
      com.sadri.core.data.entity.CategoryEntity(id = 2, name = "sink")
    )
    loadCategoriesUseCase.stub {
      onBlocking { invoke() } doReturn com.sadri.core.data.entity.Result.Success(categories)
    }

    // when
    categoryViewModel = CategoryViewModel(
      loadCategoriesUseCase = loadCategoriesUseCase,
      loadKittensUseCase = loadKittensUseCase,
      dispatcherProvider = dispatcherProvider,
      categoryItemMapper = categoryItemMapper
    )

    // then
    verifyBlocking(loadCategoriesUseCase) { invoke() }
  }


  @Test
  fun `given viewModel retry then viewModel should invoke loadCategoriesUseCase`() = runTest {
    // given
    val categories = listOf(
      com.sadri.core.data.entity.CategoryEntity(id = 1, name = "box"),
      com.sadri.core.data.entity.CategoryEntity(id = 2, name = "sink")
    )
    loadCategoriesUseCase.stub {
      onBlocking { invoke() } doReturn com.sadri.core.data.entity.Result.Success(categories)
    }

    // when
    categoryViewModel = CategoryViewModel(
      loadCategoriesUseCase = loadCategoriesUseCase,
      loadKittensUseCase = loadKittensUseCase,
      dispatcherProvider = dispatcherProvider,
      categoryItemMapper = categoryItemMapper
    )
    categoryViewModel.retry()

    // then
    verifyBlocking(loadCategoriesUseCase, times(2)) { invoke() }
  }


  @Test
  fun `given invoke viewModel then should invoke loadKittensUseCase for pictures`() =
    runTest {
      // given
      val categoryId = 1L
      val categories = listOf(
        com.sadri.core.data.entity.CategoryEntity(id = categoryId, name = "box"),
      )
      val kittens = listOf(
        com.sadri.core.data.entity.KittenEntity(id = "br", "", 123, 123),
        com.sadri.core.data.entity.KittenEntity(id = "xc", "", 123, 123)
      )
      loadCategoriesUseCase.stub {
        onBlocking { invoke() } doReturn com.sadri.core.data.entity.Result.Success(categories)
      }
      loadKittensUseCase.stub {
        onBlocking { invoke(categoryId) } doReturn com.sadri.core.data.entity.Result.Success(kittens)
      }

      // when
      categoryViewModel = CategoryViewModel(
        loadCategoriesUseCase = loadCategoriesUseCase,
        loadKittensUseCase = loadKittensUseCase,
        dispatcherProvider = dispatcherProvider,
        categoryItemMapper = categoryItemMapper
      )

      // then
      verifyBlocking(loadKittensUseCase) { invoke(categoryId) }
    }
}