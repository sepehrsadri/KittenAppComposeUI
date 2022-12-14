package com.sadri.shared.domain.repository

import com.sadri.shared.data.entity.CategoryEntity
import com.sadri.shared.data.entity.KittenEntity
import com.sadri.shared.data.entity.Result

interface KittenRepository {
  suspend fun categories(): Result<List<CategoryEntity>>

  suspend fun kittens(categoryId: Long): Result<List<KittenEntity>>
}