package com.domain.repository

import com.sadri.core.data.entity.KittenEntity
import com.sadri.core.data.entity.Result
import com.sadri.core.data.mapper.DataErrorMapper
import com.sadri.core.domain.executor.DispatcherProvider

abstract class KittenRepository(
  dataErrorMapper: DataErrorMapper,
  dispatcherProvider: DispatcherProvider
) : com.sadri.core.domain.repository.BaseRepository(dataErrorMapper, dispatcherProvider) {
  abstract suspend fun kittens(categoryId: Long): Result<List<KittenEntity>>
}