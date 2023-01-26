package com.sadri.core.data.entity.exception

class NetworkException : KittenException {
  constructor(errorMessage: String?, cause: Throwable?) : super(errorMessage, cause)
  constructor(cause: Throwable?) : super(null, cause)
}