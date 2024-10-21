package com.sd.lib.compose.active

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.CoroutineScope

private val LocalActive = compositionLocalOf<Boolean?> { null }

/**
 * 当前位置是否处于激活状态
 */
@Composable
fun fIsActive(): Boolean {
   return checkNotNull(LocalActive.current) { "Not in FActive scope." }
}

/**
 * 根据[minActiveState]决定[content]是否处于激活状态，当状态大于等于[minActiveState]时处于激活状态
 */
@Composable
fun FActiveLifecycle(
   minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
   content: @Composable () -> Unit,
) {
   val lifecycleOwner = LocalLifecycleOwner.current

   var lifecycleActive by remember(lifecycleOwner, minActiveState) {
      mutableStateOf(lifecycleOwner.lifecycle.currentState.isAtLeast(minActiveState))
   }

   DisposableEffect(lifecycleOwner, minActiveState) {
      val observer = LifecycleEventObserver { _, event ->
         if (event != Lifecycle.Event.ON_ANY) {
            lifecycleActive = event.targetState.isAtLeast(minActiveState)
         }
      }
      lifecycleOwner.lifecycle.addObserver(observer)
      onDispose {
         lifecycleOwner.lifecycle.removeObserver(observer)
      }
   }

   FActive(
      active = lifecycleActive,
      content = content,
   )
}

/**
 * 根据[active]决定[content]是否处于激活状态
 */
@Composable
fun FActive(
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
 * 当[keys]或者当前位置的激活状态发生变化时，会触发[block]
 */
@Composable
fun FActiveLaunchedEffect(
   vararg keys: Any?,
   block: suspend CoroutineScope.(isActive: Boolean) -> Unit,
) {
   val blockUpdated by rememberUpdatedState(block)
   val isActive = fIsActive()
   LaunchedEffect(isActive, *keys) {
      blockUpdated(isActive)
   }
}

/**
 * 至少激活过一次，才会显示[content]
 */
@Composable
fun FActiveAtLeastOnce(
   content: @Composable () -> Unit,
) {
   val isActive = fIsActive()
   var hasActive by remember { mutableStateOf(isActive) }

   if (hasActive || isActive) {
      hasActive = true
      content()
   }
}