package com.example.android.composable.content_horizontal

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android.ui.grey

object HorizontalContentHeaderConfig {
  val Default = Modifier
    .fillMaxWidth()
    .padding(start = 16.dp, end = 12.dp, bottom = 4.dp)
  val NulalbleStart = Modifier
    .fillMaxWidth()
    .padding(start = 0.dp, end = 12.dp, bottom = 4.dp)
  val Home = Modifier
    .fillMaxWidth()
    .padding(start = 12.dp, end = 0.dp, bottom = 4.dp)
  val fillWidth = Modifier.fillMaxWidth()
  val Detail = Modifier
    .fillMaxWidth()
    .padding(start = 0.dp, end = 0.dp, bottom = 4.dp)
}

@Composable
fun HorizontalContentHeader(
  modifier: Modifier = Modifier,
  title: String,
  onButtonClick: (() -> Unit)? = null
) {
  Row(
    modifier = modifier
      .heightIn(min = 32.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Text(
      text = title,
      style = TextStyle(
        color = MaterialTheme.colors.primary,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
      )
    )
    Spacer(modifier = Modifier.weight(1f))

    if (onButtonClick != null) {
      IconButton(onClick = onButtonClick
      ) {
        Icon(
          imageVector = Icons.Default.ArrowForward,
          contentDescription = "See all",
          tint = grey
        )
      }
    }
  }
}