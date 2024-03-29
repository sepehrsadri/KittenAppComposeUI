package com.sadri.core.domain.executor

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DispatcherProviderImpl : DispatcherProvider {
  override val ui: CoroutineDispatcher
    get() = Dispatchers.Main
  override val io: CoroutineDispatcher
    get() = Dispatchers.IO
  override val default: CoroutineDispatcher
    get() = Dispatchers.Default
}