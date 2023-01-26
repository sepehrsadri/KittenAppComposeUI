package com.sadri.core.domain.executor

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
  val ui: CoroutineDispatcher
  val io: CoroutineDispatcher
  val default: CoroutineDispatcher
}