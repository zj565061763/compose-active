package com.sd.demo.compose.active

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.sd.demo.compose.active.theme.AppTheme
import com.sd.lib.compose.active.FSetActivePage
import com.sd.lib.compose.active.fIsActive

class SampleActivePager : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      AppTheme {
        Content()
      }
    }
  }
}

@Composable
private fun Content(
  modifier: Modifier = Modifier,
) {
  val state = rememberPagerState { 5 }
  HorizontalPager(
    modifier = modifier.fillMaxSize(),
    state = state,
  ) { index ->
    state.FSetActivePage(index) {
      PageContent()
    }
  }
}

@Composable
private fun PageContent(
  modifier: Modifier = Modifier,
) {
  val color = if (fIsActive()) Color.Red else Color.Transparent
  Box(
    modifier = modifier
      .fillMaxSize()
      .background(color),
  )
}