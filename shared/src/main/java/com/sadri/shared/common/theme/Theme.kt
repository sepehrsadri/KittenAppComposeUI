package com.sadri.shared.common.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
  primary = DarkGreys10,
  primaryVariant = DarkGreys40,
  secondary = LightKitten,
  background = DarkGreys10,
  surface = DarkGreys20,
  onSurface = DarkGreys90
)

private val LightColorPalette = lightColors(
  primary = LightGreys10,
  primaryVariant = LightGreys40,
  secondary = LightKitten,
  background = LightGreys10,
  surface = LightGreys20,
  onSurface = LightGreys90
)

@Composable
fun KittenTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
  val colors = if (darkTheme) {
    DarkColorPalette
  } else {
    LightColorPalette
  }

  MaterialTheme(
    colors = colors,
    typography = Typography,
    shapes = Shapes,
    content = content
  )
}