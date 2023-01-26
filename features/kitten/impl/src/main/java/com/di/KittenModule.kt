package com.di

import com.data.repository.KittenRepositoryImpl
import com.domain.interactor.LoadKittensUseCaseImpl
import com.domain.repository.KittenRepository
import com.sadri.api.LoadKittensUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KittenModule {
  @Provides
  @Singleton
  fun bindKittenRepository(kittenRepositoryImpl: KittenRepositoryImpl): KittenRepository {
    return kittenRepositoryImpl
  }

  @Provides
  @Singleton
  fun bindKittenUseCase(loadKittensUseCaseImpl: LoadKittensUseCaseImpl): LoadKittensUseCase {
    return loadKittensUseCaseImpl
  }
}