package com.sadri.shared.common.compose

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sadri.shared.R
import com.sadri.shared.common.theme.LightKitten
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DrawerScaffoldWithTopBar(
  modifier: Modifier = Modifier,
  title: String,
  darkTheme: Boolean,
  onThemeChanged: (Boolean) -> Unit,
  content: @Composable () -> Unit
) {
  val scaffoldState = rememberScaffoldState()
  val coroutineScope = rememberCoroutineScope()
  val context = LocalContext.current.applicationContext

  Scaffold(
    modifier = modifier,
    scaffoldState = scaffoldState,
    topBar = {
      KittenTopAppBar(title = title) {
        coroutineScope.launch {
          scaffoldState.drawerState.open()
        }
      }
    },
    backgroundColor = MaterialTheme.colors.primary,
    drawerContent = {
      DrawerContent(
        darkTheme = darkTheme,
        onThemeChanged = onThemeChanged
      ) { item ->
        when (item) {
          is NavigationDrawerItem.SimpleItem -> {
            Toast
              .makeText(context, item.label, Toast.LENGTH_SHORT)
              .show()
            coroutineScope.launch {
              // delay for the ripple effect
              delay(timeMillis = 250)
              scaffoldState.drawerState.close()
            }
          }

          is NavigationDrawerItem.ThemeSwitcher -> {

          }
        }
      }
    }
  ) {
    content.invoke()
  }
}

@Composable
private fun KittenTopAppBar(
  title: String,
  onNavIconClick: () -> Unit
) {
  TopAppBar(
    backgroundColor = LightKitten,
    title = {
      Text(
        text = title,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Start,
        color = MaterialTheme.colors.primary,
        style = MaterialTheme.typography.h1
      )
    },
    navigationIcon = {
      IconButton(
        onClick = {
          onNavIconClick()
        }
      ) {
        Icon(
          imageVector = Icons.Filled.Menu,
          contentDescription = "Open Navigation Drawer"
        )
      }
    }
  )
}

@Composable
private fun DrawerContent(
  gradientColors: List<Color> = listOf(
    MaterialTheme.colors.primaryVariant,
    MaterialTheme.colors.secondary
  ),
  darkTheme: Boolean,
  onThemeChanged: (Boolean) -> Unit,
  itemClick: (NavigationDrawerItem) -> Unit
) {
  val itemsList = prepareNavigationDrawerItems()

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(brush = Brush.verticalGradient(colors = gradientColors)),
    horizontalAlignment = Alignment.CenterHorizontally,
    contentPadding = PaddingValues(vertical = 36.dp)
  ) {

    item {

      // App Icon
      Image(
        modifier = Modifier
          .size(size = 120.dp)
          .clip(shape = CircleShape),
        painter = painterResource(id = R.drawable.friendly_kitten_icon),
        contentDescription = "App Icon"
      )

      // App Name
      Text(
        modifier = Modifier
          .padding(top = 12.dp),
        text = "Kitten",
        style = MaterialTheme.typography.h2,
        color = MaterialTheme.colors.onSurface
      )

      // Developer email
      Text(
        modifier = Modifier.padding(top = 8.dp, bottom = 30.dp),
        text = "sepehrsadri@gmail.com",
        style = MaterialTheme.typography.h2,
        color = MaterialTheme.colors.onSurface
      )
    }

    items(itemsList) { item ->
      NavigationListItem(
        item = item,
        onItemClick = { itemClick(item) },
        darkTheme = darkTheme,
        onThemeChanged = onThemeChanged
      )
    }
  }
}

@Composable
private fun Switcher(name: String, change: Boolean, onCheckedChange: (Boolean) -> Unit) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 24.dp, vertical = 10.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Switch(
      checked = change,
      onCheckedChange = onCheckedChange,
      colors = SwitchDefaults.colors(
        checkedThumbColor = Color.Green,
        uncheckedThumbColor = Color.Red,
        checkedTrackColor = Color.Yellow,
        uncheckedTrackColor = Color.Black
      )
    )
    Text(
      text = name,
      modifier = Modifier.padding(start = 16.dp),
      style = MaterialTheme.typography.h2,
      color = MaterialTheme.colors.onSurface
    )
  }
}

@Composable
private fun NavigationListItem(
  item: NavigationDrawerItem,
  unreadBubbleColor: Color = MaterialTheme.colors.secondary,
  onItemClick: (NavigationDrawerItem) -> Unit,
  darkTheme: Boolean,
  onThemeChanged: (Boolean) -> Unit,
) {
  when (item) {
    is NavigationDrawerItem.SimpleItem -> {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clickable {
            onItemClick(item)
          }
          .padding(horizontal = 24.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {

        // icon and unread bubble
        Box {

          Icon(
            modifier = Modifier
              .padding(all = if (item.showUnreadBubble) 5.dp else 2.dp)
              .size(size = if (item.showUnreadBubble) 28.dp else 32.dp),
            painter = item.image,
            contentDescription = item.label,
            tint = MaterialTheme.colors.onSurface
          )

          // unread bubble
          if (item.showUnreadBubble) {
            Box(
              modifier = Modifier
                .size(size = 8.dp)
                .align(alignment = Alignment.TopEnd)
                .background(color = unreadBubbleColor, shape = CircleShape)
            )
          }
        }

        // label
        Text(
          modifier = Modifier.padding(start = 16.dp),
          text = item.label,
          style = MaterialTheme.typography.h2,
          color = MaterialTheme.colors.onSurface
        )
      }
    }

    is NavigationDrawerItem.ThemeSwitcher -> {
      var selected by remember { mutableStateOf(darkTheme) }
      Switcher(name = "Dark Theme", change = selected) {
        selected = it
        onThemeChanged(it)
      }
    }
  }
}

@Composable
private fun prepareNavigationDrawerItems(): List<NavigationDrawerItem> {
  val itemsList = arrayListOf<NavigationDrawerItem>()

  itemsList.add(NavigationDrawerItem.ThemeSwitcher)

  itemsList.add(
    NavigationDrawerItem.SimpleItem(
      image = painterResource(id = R.drawable.contact_us_icon),
      label = "Contact Us",
      showUnreadBubble = true
    )
  )
  itemsList.add(
    NavigationDrawerItem.SimpleItem(
      image = painterResource(id = R.drawable.about_us_icon),
      label = "About Us",
      showUnreadBubble = true
    )
  )
  return itemsList
}


sealed class NavigationDrawerItem {
  data class SimpleItem(
    val image: Painter,
    val label: String,
    val showUnreadBubble: Boolean = false
  ) : NavigationDrawerItem()

  object ThemeSwitcher : NavigationDrawerItem()
}