package com.sadri.shared.data.mapper

import com.sadri.shared.data.entity.exception.KittenException
import com.sadri.shared.data.entity.exception.NetworkException
import com.sadri.shared.data.entity.exception.TimeoutException
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataErrorMapper @Inject constructor() {
  fun mapToKittenException(throwable: Throwable): KittenException {
    Timber.e("DataErrorMapper : $throwable")
    return when (throwable) {
      is IOException -> NetworkException(throwable)
      is HttpException -> {
        var errorException: String? = null
        try {
          errorException = throwable.response()!!.errorBody()!!.string()
        } catch (exception: Exception) {
          exception.printStackTrace()
        }
        NetworkException(errorException, throwable)
      }
      is TimeoutCancellationException -> TimeoutException()
      else -> KittenException(null, throwable)
    }
  }
}