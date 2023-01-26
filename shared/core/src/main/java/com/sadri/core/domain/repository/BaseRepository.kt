package com.sadri.core.domain.repository

import com.sadri.core.domain.executor.DispatcherProvider
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import com.sadri.core.data.entity.Result
import com.sadri.core.data.entity.ResultWithCode
import com.sadri.core.data.mapper.DataErrorMapper

abstract class BaseRepository(
  protected val dataErrorMapper: DataErrorMapper,
  private val dispatcher: DispatcherProvider
) {
  suspend fun <T> getResult(call: suspend () -> T): Result<T> =
    withContext(dispatcher.io) {
      try {
        val response = call()
        Result.Success(response)
      } catch (throwable: Throwable) {
        Result.Failed(dataErrorMapper.mapToKittenException(throwable))
      }
    }

  suspend fun <T> getResultWithCode(call: suspend () -> Response<T>): ResultWithCode<T> =
    withContext(dispatcher.io) {
      try {
        val response = call()
        ResultWithCode(
          result = wrapResult(response),
          code = response.code()
        )
      } catch (throwable: Throwable) {
        ResultWithCode(
          result = Result.Failed(dataErrorMapper.mapToKittenException(throwable)),
          code = ResultWithCode.NO_RESPONSE_CODE,
        )
      }
    }

  // inspired by retrofit.KotlinExtensions
  private fun <T> wrapResult(response: Response<T>): Result<T> {
    return if (response.isSuccessful) {
      val body = response.body()
      if (body == null) {
        val exception =
          KotlinNullPointerException("Response was null but response body type was declared as non-null")
        Result.Failed(dataErrorMapper.mapToKittenException(exception))
      } else {
        Result.Success(body)
      }
    } else {
      val exception = HttpException(response)
      Result.Failed(dataErrorMapper.mapToKittenException(exception))
    }
  }
}