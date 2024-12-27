package com.sd.lib.compose.active

import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun FSetActivePager(
  state: PagerState,
  page: Int,
  content: @Composable () -> Unit,
) {
  var active by remember { mutableStateOf(false) }

  if (!state.isScrollInProgress) {
    active = page == state.currentPage
  }

  FSetActive(
    active = active,
    content = content,
  )
}