package com.sadri.shared.common.compose

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun KittenItemsLoadingFailed() {
  Text(text = "Failed to load items :(")
}