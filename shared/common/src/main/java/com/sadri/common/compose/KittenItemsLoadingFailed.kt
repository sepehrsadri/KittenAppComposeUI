package com.sadri.common.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sadri.common.theme.LightKitten
import com.sadri.shared_common.R

@Composable
fun KittenItemsLoadingFailed(
  darkTheme: Boolean,
  onThemeChanged: (Boolean) -> Unit,
  retry: () -> Unit
) {
  DrawerScaffoldWithTopBar(
    title = "Loading Failed :(",
    darkTheme = darkTheme,
    onThemeChanged = onThemeChanged
  ) {
    Box(modifier = Modifier.fillMaxSize()) {
      Column(Modifier.align(Alignment.Center)) {
        Image(
          painter = painterResource(id = R.drawable.sleeping_kitty),
          contentDescription = "sleeping kitty",
          modifier = Modifier
            .weight(1f)
            .padding(64.dp)
        )
        TextButton(
          modifier = Modifier
            .padding(8.dp)
            .height(64.dp)
            .fillMaxWidth(),
          onClick = retry,
          colors = ButtonDefaults.buttonColors(
            backgroundColor = LightKitten
          )
        ) {
          Text(
            text = "Retry",
            style = MaterialTheme.typography.h2,
            color = MaterialTheme.colors.primary
          )
        }
      }
    }
  }

}