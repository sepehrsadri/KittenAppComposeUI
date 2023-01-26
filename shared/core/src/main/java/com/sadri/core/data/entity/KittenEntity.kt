package com.sadri.core.data.entity

import com.google.gson.annotations.SerializedName

data class KittenEntity(
  @SerializedName("id")
  val id: String,
  @SerializedName("url")
  val url: String,
  @SerializedName("width")
  val width: Long,
  @SerializedName("height")
  val height: Long
)
