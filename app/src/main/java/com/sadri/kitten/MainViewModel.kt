package com.sadri.kitten

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
  var darkThemeState by mutableStateOf(MainScreenState())
    private set

  fun onDarkThemeChanged(darkTheme: Boolean) {
    viewModelScope.launch {
      darkThemeState = darkThemeState.copy(darkTheme = darkTheme)
    }
  }
}

data class MainScreenState(
  val darkTheme: Boolean = true
)