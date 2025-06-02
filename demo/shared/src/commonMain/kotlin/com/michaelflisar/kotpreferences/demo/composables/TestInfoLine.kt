package com.michaelflisar.kotpreferences.demo.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TestInfoLine(
    label: String,
    info: String
) {
    Row {
        Text(label, style = MaterialTheme.typography.titleSmall, modifier = Modifier.weight(1f))
        Text(
            "=",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
        Text(info, style = MaterialTheme.typography.bodySmall, modifier = Modifier.weight(1f))
    }
}