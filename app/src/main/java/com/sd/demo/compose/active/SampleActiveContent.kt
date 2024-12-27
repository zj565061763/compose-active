package com.sd.demo.compose.active

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sd.demo.compose.active.theme.AppTheme
import com.sd.lib.compose.active.FActive
import com.sd.lib.compose.active.FSetActive

class SampleActiveContent : ComponentActivity() {
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
  var active by remember { mutableStateOf(false) }

  Column(
    modifier = modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Switch(
      checked = active,
      onCheckedChange = { active = it },
    )
    FSetActive(active) {
      ActiveContent()
    }
  }
}

@Composable
private fun ActiveContent(
  modifier: Modifier = Modifier,
) {
  Box(modifier = modifier) {
    FActive(
      getInactiveTimeout = { 1_000 },
      default = {
        Text(text = "default")
      },
      content = {
        Text(text = "content")
      },
    )
  }
}