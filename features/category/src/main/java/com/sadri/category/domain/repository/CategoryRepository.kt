package com.sadri.category.domain.repository

import com.sadri.core.data.entity.CategoryEntity
import com.sadri.core.data.entity.Result
import com.sadri.core.data.mapper.DataErrorMapper
import com.sadri.core.domain.executor.DispatcherProvider
import com.sadri.core.domain.repository.BaseRepository

abstract class CategoryRepository constructor(
  dataErrorMapper: DataErrorMapper,
  dispatcher: DispatcherProvider
) : BaseRepository(dataErrorMapper, dispatcher) {
  abstract suspend fun categories(): Result<List<CategoryEntity>>
}