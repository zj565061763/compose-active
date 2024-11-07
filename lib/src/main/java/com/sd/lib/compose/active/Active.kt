package com.sd.lib.compose.active

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

private val LocalActive = compositionLocalOf<Boolean?> { null }

/**
 * 当前位置是否处于激活状态
 */
@Composable
fun fIsActive(): Boolean {
   return checkNotNull(LocalActive.current) { "Not in FActive scope." }
}

/**
 * 根据[active]决定[content]是否处于激活状态
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

/**
 * 至少激活过一次，才会显示[content]
 */
@Composable
fun FActiveAtLeastOnce(
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