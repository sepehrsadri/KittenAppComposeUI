package com.sadri.network.datasource


import com.sadri.core.data.entity.CategoryEntity
import com.sadri.core.data.entity.KittenEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface KittenDataSource {
  @GET("categories")
  suspend fun categories(): List<CategoryEntity>

  @GET("images/search?limit=10")
  suspend fun kittens(@Query("category_ids") categoryId: Long): List<KittenEntity>
}