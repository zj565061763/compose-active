package com.sd.lib.compose.active

import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable

@Composable
fun PagerState.FActiveIndex(
   index: Int,
   content: @Composable () -> Unit,
) {
   val active = !isScrollInProgress && currentPage == index
   FActive(
      active = active,
      content = content,
   )
}