package com.sadri.shared.data.repository

import com.sadri.shared.data.datasource.KittenDataSource
import com.sadri.shared.data.entity.CategoryEntity
import com.sadri.shared.data.entity.KittenEntity
import com.sadri.shared.data.entity.Result
import com.sadri.shared.data.mapper.DataErrorMapper
import com.sadri.shared.domain.executor.DispatcherProvider
import com.sadri.shared.domain.repository.BaseRepository
import com.sadri.shared.domain.repository.KittenRepository
import javax.inject.Inject

class KittenRepositoryImpl @Inject constructor(
  dispatcherProvider: DispatcherProvider,
  dataErrorMapper: DataErrorMapper,
  private val kittenDataSource: KittenDataSource
) : BaseRepository(dataErrorMapper, dispatcherProvider), KittenRepository {
  override suspend fun categories(): Result<List<CategoryEntity>> = getResult {
    kittenDataSource.categories()
  }

  override suspend fun kittens(categoryId: Long): Result<List<KittenEntity>> = getResult {
    kittenDataSource.kittens(categoryId)
  }
}