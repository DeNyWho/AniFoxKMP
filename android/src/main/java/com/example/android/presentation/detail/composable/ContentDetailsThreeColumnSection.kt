package com.example.android.presentation.detail.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android.ui.orange400
import com.example.android.ui.red
import com.example.common.models.common.ContentDetail

@Composable
fun ContentDetailsThreeColumnSection(
    modifier: Modifier = Modifier,
    data: ContentDetail
) {
    val first = data.year.toString()
    var second = when(data.season){
        "Winter" -> "Зима"
        "Spring" -> "Весна"
        "Fall" -> "Осень"
        "Summer" -> "Лето"
        else -> ""
    }
    if(second == "") second = data.status.toString()
    val third =
        if(data.episodesCount == data.episodesCountAired || data.episodesCountAired == null) "${data.episodesCount}"
        else "${data.episodesCountAired}/${data.episodesCount}"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TwoRowTextComp(title = "Год", subtitle = first)
        VerticalDivider()
        TwoRowTextComp(title = if(second.length > 3) "Сезон" else "Статус", subtitle = second)
        VerticalDivider()
        TwoRowTextComp(title = "Эпизоды", subtitle = third)
    }
}

@Composable
private fun RowScope.TwoRowTextComp(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String
) {
    Column(
        modifier = Modifier.weight(weight = 1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
            )
        )
        Text(
            text = subtitle,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.LightGray,
            )
        )
    }
}

@Composable
private fun RowScope.VerticalDivider(modifier: Modifier = Modifier) {
    TabRowDefaults.Divider(
        color = orange400,
        modifier = Modifier
            .fillMaxHeight(fraction = 0.9f)
            .width(2.dp)
    )
}