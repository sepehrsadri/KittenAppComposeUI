package com.data.mapper

import com.data.model.KittenItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KittenItemMapper @Inject constructor() {
  fun mapToKittenItems(kittenEntityList: List<com.sadri.core.data.entity.KittenEntity>): List<KittenItem> {
    return kittenEntityList.map { it.toKittenItem() }
  }

  private fun com.sadri.core.data.entity.KittenEntity.toKittenItem(): KittenItem {
    return KittenItem(id, url, width, height)
  }
}