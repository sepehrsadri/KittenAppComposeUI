package com.sadri.category.di

import com.sadri.category.data.repository.CategoryRepositoryImpl
import com.sadri.category.domain.repository.CategoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CategoryModule {
  @Provides
  @Singleton
  fun bindCategoryRepository(categoryRepositoryImpl: CategoryRepositoryImpl): CategoryRepository {
    return categoryRepositoryImpl
  }
}