package com.sadri.category.data.repository

import com.sadri.category.domain.repository.CategoryRepository
import com.sadri.core.data.entity.CategoryEntity
import com.sadri.core.data.entity.Result
import com.sadri.core.data.mapper.DataErrorMapper
import com.sadri.core.domain.executor.DispatcherProvider
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
  dispatcherProvider: DispatcherProvider,
  dataErrorMapper: DataErrorMapper,
  private val kittenDataSource: com.sadri.network.datasource.KittenDataSource
) : CategoryRepository(dataErrorMapper, dispatcherProvider) {
  override suspend fun categories(): Result<List<CategoryEntity>> =
    getResult {
      kittenDataSource.categories()
    }
}