package com.michaelflisar.kotpreferences.demo.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun MyLogInfo(
    title: String,
    info: String
) {
    Column {
        Text(title, style = MaterialTheme.typography.titleSmall)
        Text(info, style = MaterialTheme.typography.bodySmall)
    }
}