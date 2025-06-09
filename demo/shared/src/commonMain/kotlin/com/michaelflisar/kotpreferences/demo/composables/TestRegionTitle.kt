package com.michaelflisar.kotpreferences.demo.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TestRegionTitle(
    title: String
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun TestRegionTitle2(
    title: String
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        //color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 2.dp)
    )
}