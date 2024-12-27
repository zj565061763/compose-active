package com.sd.demo.compose.active

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.sd.demo.compose.active.theme.AppTheme
import com.sd.lib.compose.active.FSetActive
import com.sd.lib.compose.active.fIsActive

class SampleActive : ComponentActivity() {
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
private fun Content() {
  FSetActive(true) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)
    ) {
      StateBox(text = "1", activeColor = Color.Red) {
        StateBox(text = "2", activeColor = Color.Red) {
          StateBox(text = "3", activeColor = Color.Red) {
            ContentBox()
          }
        }
      }
    }
  }
}

@Composable
private fun StateBox(
  modifier: Modifier = Modifier,
  text: String = "",
  activeColor: Color,
  child: (@Composable () -> Unit)? = null,
) {
  var checked by remember { mutableStateOf(false) }

  ConstraintLayout(
    modifier = modifier
      .fillMaxSize()
      .padding(vertical = 48.dp, horizontal = 16.dp)
  ) {
    val (refBorder, refText, refSwitch, refChild) = createRefs()

    Box(
      modifier = Modifier
        .constrainAs(refBorder) {
          width = Dimension.matchParent
          height = Dimension.matchParent

        }
        .border(2.dp, if (fIsActive()) activeColor else Color.Gray)
    )

    Switch(
      checked = checked,
      onCheckedChange = { checked = it },
      modifier = Modifier.constrainAs(refSwitch) {
        top.linkTo(refBorder.top)
        bottom.linkTo(refBorder.top)
        centerHorizontallyTo(parent)
      }
    )

    Text(
      text = text,
      modifier = Modifier.constrainAs(refText) {
        centerVerticallyTo(refSwitch)
        end.linkTo(refSwitch.start, 8.dp)
      }
    )

    if (child != null) {
      FSetActive(checked) {
        Box(modifier = Modifier.constrainAs(refChild) {
          width = Dimension.matchParent
          height = Dimension.matchParent
        }) {
          child()
        }
      }
    }
  }
}

@Composable
private fun ContentBox(
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = modifier.fillMaxSize(),
    contentAlignment = Alignment.Center,
  ) {
    Text(
      text = "content",
      fontWeight = FontWeight.Bold,
      color = if (fIsActive()) Color.Red else Color.Gray,
    )
  }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
  AppTheme {
    Content()
  }
}