package com.sadri.core.data.entity.exception

open class KittenException @JvmOverloads constructor(
  message: String?,
  cause: Throwable? = null,
) : Exception(message, cause)
