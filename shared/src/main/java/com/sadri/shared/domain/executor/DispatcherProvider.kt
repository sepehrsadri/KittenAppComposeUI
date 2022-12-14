package com.sadri.shared.domain.executor

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
  val ui: CoroutineDispatcher
  val io: CoroutineDispatcher
  val default: CoroutineDispatcher
}