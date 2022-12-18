package com.sadri.category.data.model

data class CategoryItem(
  val id: Long,
  val title: String,
  val image: CategoryImage = CategoryImage.Unknown
)

sealed class CategoryImage {

  data class Url(val url: String) : CategoryImage()

  object Unknown : CategoryImage()
}
