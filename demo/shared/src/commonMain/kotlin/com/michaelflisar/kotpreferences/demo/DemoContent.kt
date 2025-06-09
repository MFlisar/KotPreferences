package com.michaelflisar.kotpreferences.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@Composable
fun DemoContent(
    modifier: Modifier,
    platform: String,
    ioContext: CoroutineContext,
    settings: List<Pair<String, DemoSettingsModel>>
) {
    val pagerState = rememberPagerState { settings.size }
    val tabs = settings.map { it.first }
    val scope = rememberCoroutineScope()

    Column(modifier) {
        TabRow(selectedTabIndex = pagerState.currentPage) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                    text = { Text(title) }
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->

            val pageModifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp)

            val settingsModel = settings[page].second
            DemoSettingsPage(pageModifier, platform, ioContext, settingsModel)
        }
    }
}
