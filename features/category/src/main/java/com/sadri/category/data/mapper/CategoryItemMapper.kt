package com.sadri.category.data.mapper

import com.sadri.category.data.model.CategoryItem
import com.sadri.shared.data.entity.CategoryEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryItemMapper @Inject constructor() {
  fun mapToCategoryItems(categoryEntityList: List<CategoryEntity>): List<CategoryItem> {
    return categoryEntityList.map { it.toCategoryItem() }
  }

  private fun CategoryEntity.toCategoryItem(): CategoryItem {
    return CategoryItem(id = id, title = name, image = "https://cdn2.thecatapi.com/images/7e3.jpg")
  }
}