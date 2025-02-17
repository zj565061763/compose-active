package com.sd.lib.compose.active

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

/**
 * 激活状态显示[content]，当状态由激活变为未激活时，过[getActiveTimeout]毫秒后显示[default]
 */
@Composable
inline fun FActiveContent(
  getActiveTimeout: () -> Long = { 0 },
  default: @Composable () -> Unit = {},
  content: @Composable () -> Unit,
) {
  val isActive = fIsActive()
  var hasActive by remember { mutableStateOf(isActive) }

  if (isActive) {
    hasActive = true
  } else {
    if (hasActive) {
      val timeout = getActiveTimeout()
      if (timeout > 0) {
        LaunchedEffect(Unit) {
          delay(timeout)
          hasActive = false
        }
      } else {
        hasActive = false
      }
    }
  }

  if (hasActive) {
    content()
  } else {
    default()
  }
}

/**
 * 激活过一次，就会一直显示[content]
 */
@Composable
inline fun FActiveOnceContent(
  default: @Composable () -> Unit = {},
  content: @Composable () -> Unit,
) {
  val isActive = fIsActive()
  var hasActive by remember { mutableStateOf(isActive) }

  if (hasActive || isActive) {
    hasActive = true
    content()
  } else {
    default()
  }
}