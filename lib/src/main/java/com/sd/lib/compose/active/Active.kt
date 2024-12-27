package com.sd.lib.compose.active

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

private val LocalActive = compositionLocalOf<Boolean?> { null }

/**
 * 当前位置是否处于激活状态
 */
@Composable
fun fIsActive(): Boolean {
  return checkNotNull(LocalActive.current) { "Not in active scope." }
}

/**
 * 根据[active]设置[content]是否处于激活状态
 */
@Composable
fun FSetActive(
  active: Boolean,
  content: @Composable () -> Unit,
) {
  val localActive = LocalActive.current
  val finalActive = if (localActive == null) active else (active && localActive)
  CompositionLocalProvider(LocalActive provides finalActive) {
    content()
  }
}