package com.sadri.kitten.data.mapper

import com.sadri.kitten.data.model.KittenItem
import com.sadri.shared.data.entity.KittenEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KittenItemMapper @Inject constructor() {
  fun mapToKittenItems(kittenEntityList: List<KittenEntity>): List<KittenItem> {
    return kittenEntityList.map { it.toKittenItem() }
  }

  private fun KittenEntity.toKittenItem(): KittenItem {
    return KittenItem(id, url, width, height)
  }
}