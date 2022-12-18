package com.sadri.kitten.ui

import androidx.lifecycle.SavedStateHandle
import com.sadri.kitten.data.mapper.KittenItemMapper
import com.sadri.shared.data.entity.KittenEntity
import com.sadri.shared.data.entity.Result
import com.sadri.shared.domain.interactor.LoadKittensUseCase
import com.sadri.shared.navigation.Screen
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

  private val loadKittensUseCase: LoadKittensUseCase = mock()

  private lateinit var savedStateHandle: SavedStateHandle

  private val kittenItemMapper = KittenItemMapper()

  private lateinit var kittenViewModel: KittenViewModel

  @Test
  fun `given invoke viewModel then should invoke loadKittensUseCase`() = runTest {
    // given
    savedStateHandle = SavedStateHandle()
    savedStateHandle[Screen.Kitten.KittenArgs.CategoryName] = "Boxes"
    savedStateHandle[Screen.Kitten.KittenArgs.CategoryId] = "1"
    val kittens = listOf(
      KittenEntity(id = "br", "", 123, 123),
      KittenEntity(id = "xc", "", 123, 123)
    )
    loadKittensUseCase.stub {
      onBlocking { invoke(1) } doReturn Result.Success(kittens)
    }

    // when
    kittenViewModel = KittenViewModel(
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
    savedStateHandle[Screen.Kitten.KittenArgs.CategoryName] = "Boxes"
    savedStateHandle[Screen.Kitten.KittenArgs.CategoryId] = "1"
    val kittens = listOf(
      KittenEntity(id = "br", "", 123, 123),
      KittenEntity(id = "xc", "", 123, 123)
    )
    loadKittensUseCase.stub {
      onBlocking { invoke(1) } doReturn Result.Success(kittens)
    }

    // when
    kittenViewModel = KittenViewModel(
      savedStateHandle = savedStateHandle,
      loadKittensUseCase = loadKittensUseCase,
      kittenItemMapper = kittenItemMapper
    )
    kittenViewModel.retry()

    // then
    verifyBlocking(loadKittensUseCase, times(2)) { invoke(1) }
  }


}