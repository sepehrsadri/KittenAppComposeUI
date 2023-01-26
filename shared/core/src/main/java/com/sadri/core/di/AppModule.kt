package com.sadri.core.di

import com.sadri.core.domain.executor.DispatcherProvider
import com.sadri.core.domain.executor.DispatcherProviderImpl
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
}