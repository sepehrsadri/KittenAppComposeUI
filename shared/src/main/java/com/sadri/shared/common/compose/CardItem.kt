package com.sadri.shared.common.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sadri.shared.common.theme.Typography

@Composable
fun CardItem(
  modifier: Modifier = Modifier,
  title: String? = null,
  subtitle: String? = null,
  imageUrl: String? = null,
  imageResource: Int? = null,
  onCardClick: () -> Unit
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .clickable {
        onCardClick.invoke()
      },
    elevation = 8.dp
  ) {
    Column(
      modifier = Modifier
        .padding(8.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      if (!imageUrl.isNullOrEmpty()) {
        AsyncImage(
          model = imageUrl,
          contentDescription = "$title image",
          contentScale = ContentScale.Crop,
          modifier = Modifier
            .clip(CircleShape)
            .size(120.dp)
        )
      }
      if (imageResource != null) {
        Image(
          painter = painterResource(id = imageResource),
          contentDescription = "loaded from image resources",
          contentScale = ContentScale.Crop,
          modifier = Modifier
            .clip(CircleShape)
            .size(120.dp)
        )
      }
      if (title.isNullOrEmpty().not()) {
        Spacer(modifier = Modifier.size(8.dp))
        Column(
          modifier = Modifier
            .padding(start = 8.dp)
        ) {
          Text(
            text = title!!,
            style = Typography.body1,
            color = MaterialTheme.colors.onSurface
          )
          Spacer(modifier = Modifier.size(8.dp))
          if (!subtitle.isNullOrEmpty()) {
            Text(
              text = subtitle,
              style = Typography.subtitle1,
              color = MaterialTheme.colors.onBackground
            )
          }
        }
      }
    }
  }
}