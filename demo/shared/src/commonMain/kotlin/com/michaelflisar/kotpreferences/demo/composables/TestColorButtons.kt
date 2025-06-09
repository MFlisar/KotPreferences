package com.michaelflisar.kotpreferences.demo.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun TestSelectColorButtons(
    onColorSelected: (Color) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val colors = listOf(
        "Red" to Color.Red,
        "Green" to Color.Green,
        "Blue" to Color.Blue,
        "White" to Color.White,
        "Black" to Color.Black,
    )
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        colors.forEach { color ->
            Button(onClick = {
                scope.launch {
                    onColorSelected(color.second)
                }
            }) {
                TestColor(color.first, color.second)
            }
        }
    }
}

@Composable
fun TestColor(
    label: String,
    color: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = label)
        Spacer(modifier = Modifier.size(16.dp).background(color).clip(MaterialTheme.shapes.small))
    }
}