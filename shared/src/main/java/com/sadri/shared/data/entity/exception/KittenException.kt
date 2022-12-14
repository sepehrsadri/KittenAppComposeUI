package com.sadri.shared.data.entity.exception

open class KittenException @JvmOverloads constructor(
  message: String?,
  cause: Throwable? = null,
) : Exception(message, cause)
