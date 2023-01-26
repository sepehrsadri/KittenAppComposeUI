package com.sadri.impl.ui

import androidx.lifecycle.SavedStateHandle
import com.sadri.test_utils.CoroutineTestRule
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
class KittenViewModelBehaviouralTest {

  @get:Rule
  val coroutineTestRule = CoroutineTestRule()

  private val loadKittensUseCase: com.domain.interactor.LoadKittensUseCaseImpl = mock()

  private lateinit var savedStateHandle: SavedStateHandle

  private val kittenItemMapper = com.data.mapper.KittenItemMapper()

  private lateinit var kittenViewModel: com.ui.KittenViewModel

  @Test
  fun `given invoke viewModel then should invoke loadKittensUseCase`() = runTest {
    // given
    savedStateHandle = SavedStateHandle()
    savedStateHandle[com.sadri.core.navigation.Screen.Kitten.KittenArgs.CategoryName] = "Boxes"
    savedStateHandle[com.sadri.core.navigation.Screen.Kitten.KittenArgs.CategoryId] = "1"
    val kittens = listOf(
      com.sadri.core.data.entity.KittenEntity(id = "br", "", 123, 123),
      com.sadri.core.data.entity.KittenEntity(id = "xc", "", 123, 123)
    )
    loadKittensUseCase.stub {
      onBlocking { invoke(1) } doReturn com.sadri.core.data.entity.Result.Success(kittens)
    }

    // when
    kittenViewModel = com.ui.KittenViewModel(
      savedStateHandle = savedStateHandle,
      loadKittensUseCase = loadKittensUseCase,
      kittenItemMapper = kittenItemMapper
    )

    // then
    verifyBlocking(loadKittensUseCase) { invoke(1) }
  }

  @Test
  fun `given viewModel retry then should invoke loadKittensUseCase`() = runTest {
    // given
    savedStateHandle = SavedStateHandle()
    savedStateHandle[com.sadri.core.navigation.Screen.Kitten.KittenArgs.CategoryName] = "Boxes"
    savedStateHandle[com.sadri.core.navigation.Screen.Kitten.KittenArgs.CategoryId] = "1"
    val kittens = listOf(
      com.sadri.core.data.entity.KittenEntity(id = "br", "", 123, 123),
      com.sadri.core.data.entity.KittenEntity(id = "xc", "", 123, 123)
    )
    loadKittensUseCase.stub {
      onBlocking { invoke(1) } doReturn com.sadri.core.data.entity.Result.Success(kittens)
    }

    // when
    kittenViewModel = com.ui.KittenViewModel(
      savedStateHandle = savedStateHandle,
      loadKittensUseCase = loadKittensUseCase,
      kittenItemMapper = kittenItemMapper
    )
    kittenViewModel.retry()

    // then
    verifyBlocking(loadKittensUseCase, times(2)) { invoke(1) }
  }


}