package com.sadri.test_utils

import com.sadri.shared.domain.executor.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
object TestDispatcherProvider : DispatcherProvider {
  val testDispatcher = UnconfinedTestDispatcher()

  override val ui: CoroutineDispatcher
    get() = testDispatcher
  override val io: CoroutineDispatcher
    get() = testDispatcher
  override val default: CoroutineDispatcher
    get() = testDispatcher
}