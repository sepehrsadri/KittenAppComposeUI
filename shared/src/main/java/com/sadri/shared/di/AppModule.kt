package com.sadri.shared.di

import com.sadri.shared.data.repository.KittenRepositoryImpl
import com.sadri.shared.domain.executor.DispatcherProvider
import com.sadri.shared.domain.executor.DispatcherProviderImpl
import com.sadri.shared.domain.repository.KittenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  @Provides
  @Singleton
  fun provideDispatcherProvider(): DispatcherProvider = DispatcherProviderImpl()

  @Provides
  @Singleton
  fun bindKittenRepository(kittenRepositoryImpl: KittenRepositoryImpl): KittenRepository {
    return kittenRepositoryImpl
  }
}