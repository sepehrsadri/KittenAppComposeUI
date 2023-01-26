package com.sadri.api

import com.sadri.core.data.entity.KittenEntity
import com.sadri.core.data.entity.Result

interface LoadKittensUseCase {
  suspend fun load(categoryId: Long): Result<List<KittenEntity>>
}