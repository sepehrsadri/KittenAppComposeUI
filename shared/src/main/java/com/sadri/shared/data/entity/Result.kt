package com.sadri.shared.data.entity

import com.sadri.shared.data.entity.exception.KittenException

sealed class Result<out T> {
  data class Success<T>(val data: T) : Result<T>()
  data class Failed(val exception: KittenException) : Result<Nothing>()
}

typealias CompletableResult = Result<Unit>

data class ResultWithCode<T>(
  val result: Result<T>,
  val code: Int,
) {
  companion object {
    const val NO_RESPONSE_CODE = 0
  }
}

inline fun <T, S> Result<T>.map(transform: (T) -> S): Result<S> {
  return when (this) {
    is Result.Success -> Result.Success(transform(data))
    is Result.Failed -> this
  }
}

inline fun <T> Result<T>.onSuccess(block: (T) -> Unit): Result<T> {
  if (this is Result.Success) {
    block(data)
  }
  return this
}

inline fun <T> Result<T>.onFailed(block: (KittenException) -> Unit): Result<T> {
  if (this is Result.Failed) {
    block(exception)
  }
  return this
}
