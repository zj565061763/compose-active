package com.sd.demo.compose.active

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.sd.demo.compose.active.theme.AppTheme
import com.sd.lib.compose.active.FSetActivePage
import com.sd.lib.compose.active.fIsActive
import kotlin.math.roundToInt

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
  val state = rememberPagerState { 100 }
  HorizontalPager(
    modifier = modifier.fillMaxSize(),
    state = state,
    pageSpacing = 10.dp,
    pageSize = object : PageSize {
      override fun Density.calculateMainAxisPageSize(availableSpace: Int, pageSpacing: Int): Int {
        return (availableSpace * 0.8f).roundToInt()
      }
    },
    snapPosition = SnapPosition.Center,
  ) { page ->
    state.FSetActivePage(page) {
      PageContent()
    }
  }
}

@Composable
private fun PageContent(
  modifier: Modifier = Modifier,
) {
  val color = if (fIsActive()) Color.Red else Color.Gray
  Box(
    modifier = modifier
      .background(color)
      .fillMaxWidth()
      .aspectRatio(1f),
  )
}