package com.sd.lib.compose.active

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@Composable
fun <T> StateFlow<T>.fCollectAsStateWithActive(
   context: CoroutineContext = EmptyCoroutineContext,
): State<T> {
   @SuppressLint("StateFlowValueCalledInComposition")
   val initialValue = this.value
   return collectAsStateWithActive(
      initialValue = initialValue,
      context = context,
   )
}

@Composable
fun <T> Flow<T>.fCollectAsStateWithActive(
   initialValue: T,
   context: CoroutineContext = EmptyCoroutineContext,
): State<T> {
   return collectAsStateWithActive(
      initialValue = initialValue,
      context = context,
   )
}

@Composable
private fun <T> Flow<T>.collectAsStateWithActive(
   initialValue: T,
   context: CoroutineContext = EmptyCoroutineContext,
): State<T> {
   val flow = this
   return remember { mutableStateOf(initialValue) }.also { state ->
      if (fIsActive()) {
         LaunchedEffect(flow, context) {
            if (context == EmptyCoroutineContext) {
               flow.collect { state.value = it }
            } else withContext(context) {
               flow.collect { state.value = it }
            }
         }
      }
   }
}