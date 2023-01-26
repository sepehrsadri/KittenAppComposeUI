package com.data.repository

import com.domain.repository.KittenRepository
import com.sadri.core.data.entity.KittenEntity
import com.sadri.core.data.entity.Result
import com.sadri.core.data.mapper.DataErrorMapper
import com.sadri.core.domain.executor.DispatcherProvider
import javax.inject.Inject

class KittenRepositoryImpl @Inject constructor(
  dispatcherProvider: DispatcherProvider,
  dataErrorMapper: DataErrorMapper,
  private val kittenDataSource: com.sadri.network.datasource.KittenDataSource
) : KittenRepository(dataErrorMapper, dispatcherProvider) {
  override suspend fun kittens(categoryId: Long): Result<List<KittenEntity>> =
    getResult {
      kittenDataSource.kittens(categoryId)
    }
}