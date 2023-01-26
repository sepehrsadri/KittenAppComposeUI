package com.sadri.common.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun KittenProgressItem() {
  Box(
    modifier = Modifier
      .fillMaxSize()
  ) {
    CircularProgressIndicator(
      color = MaterialTheme.colors.secondary,
      modifier = Modifier
        .size(64.dp)
        .padding(16.dp)
        .align(Alignment.Center)
    )
  }

}