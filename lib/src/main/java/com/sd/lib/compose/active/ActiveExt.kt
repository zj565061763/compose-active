package com.sd.lib.compose.active

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@Composable
private fun <T> StateFlow<T>.fCollectAsStateWithActive(
   context: CoroutineContext = EmptyCoroutineContext,
): State<T> {
   val flow = this
   @SuppressLint("StateFlowValueCalledInComposition")
   val initialValue = flow.value
   val isActive = fIsActive()
   return produceState(initialValue = initialValue, flow, isActive, context) {
      if (isActive) {
         if (context == EmptyCoroutineContext) {
            flow.collect { this@produceState.value = it }
         } else {
            withContext(context) {
               flow.collect { this@produceState.value = it }
            }
         }
      }
   }
}