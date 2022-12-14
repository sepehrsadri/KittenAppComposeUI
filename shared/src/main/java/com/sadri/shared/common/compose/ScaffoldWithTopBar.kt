package com.sadri.shared.common.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.sadri.shared.common.theme.LightGreys90
import com.sadri.shared.common.theme.LightKitten

@Composable
fun ScaffoldWithTopBar(
  title: String,
  content: @Composable () -> Unit
) {
  Scaffold(
    topBar = {
      TopAppBar(
        backgroundColor = LightKitten,
        title = {
          Text(
            text = title,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = LightGreys90
          )
        })
    },
    backgroundColor = MaterialTheme.colors.primary
  ) {
    content.invoke()
  }
}