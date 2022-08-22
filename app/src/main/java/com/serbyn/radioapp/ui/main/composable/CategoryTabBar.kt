@file:OptIn(ExperimentalPagerApi::class)

package com.serbyn.radioapp.ui.main.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import com.serbyn.radioapp.ui.main.MainContract
import kotlinx.coroutines.launch

@Composable
fun CategoryTabBar(
    state: MainContract.State,
    onOutlineChosen: OnOutlineChosen,
    onTabSwitched: (Int) -> Unit,
) {
    val tabs = listOf(
        TabItem.Local(state.catalogList[0]) {
            onOutlineChosen(it)
        },
        TabItem.Music(state.catalogList[1]),
        TabItem.Talk(state.catalogList[2]),
        TabItem.Sports(state.catalogList[3]),
        TabItem.ByLocation(state.catalogList[4]),
        TabItem.ByLanguage(state.catalogList[5]),
        TabItem.Podcasts(state.catalogList[6]),
    )
    val pagerState = rememberPagerState(initialPage = state.selectedTabIndex ?: 0)
    Column(modifier = Modifier.padding(8.dp)) {
        Tabs(state = state, pagerState = pagerState, onTabSwitched)
        TabsContent(tabs = tabs, pagerState = pagerState)
    }
}

@Composable
fun Tabs(state: MainContract.State, pagerState: PagerState, onTabSwitched: (Int) -> Unit) {
    val scope = rememberCoroutineScope()

    ScrollableTabRow(
        // Our selected tab is our current page
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = MaterialTheme.colorScheme.primary,
        contentColor = Color.White,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }) {

        val tabs = state.catalogList
        tabs.forEachIndexed { index, tab ->
            Tab(
                text = { Text(tab.text) },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                        onTabSwitched(index)
                    }
                },
            )
        }
    }
}

@Composable
fun TabsContent(tabs: List<TabItem>, pagerState: PagerState) {
    HorizontalPager(state = pagerState, count = tabs.size) { page ->
        tabs[page].screen()
    }
}