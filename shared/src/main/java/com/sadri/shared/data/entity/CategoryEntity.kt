package com.sadri.shared.data.entity

import com.google.gson.annotations.SerializedName

data class CategoryEntity(
  @SerializedName("id")
  val id: Long,
  @SerializedName("name")
  val name: String
)
