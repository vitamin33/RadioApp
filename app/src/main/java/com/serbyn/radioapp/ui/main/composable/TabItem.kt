package com.serbyn.radioapp.ui.main.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serbyn.radioapp.ui.base.composable.ErrorScreen
import com.serbyn.radioapp.ui.base.composable.LoadingScreen
import com.serbyn.radioapp.ui.main.entity.Catalog
import com.serbyn.radioapp.ui.main.entity.Outline

typealias ComposableFun = @Composable () -> Unit

typealias OnOutlineChosen = (item: Outline) -> Unit

sealed class TabItem(var screen: ComposableFun) {
    data class Local(val catalog: Catalog, val onOutlineChosen: OnOutlineChosen) : TabItem({
        StationScreen(catalog) {
            onOutlineChosen(it)
        }
    })
    data class Music(val catalog: Catalog) : TabItem({
        MusicScreen(catalog)
    })
    data class Talk(val catalog: Catalog) : TabItem({
        TalkScreen(catalog)
    })
    data class Sports(val catalog: Catalog) : TabItem({
        SportsScreen(catalog)
    })
    data class ByLocation(val catalog: Catalog): TabItem({
        ByLocationScreen(catalog)
    })
    data class ByLanguage(val catalog: Catalog) : TabItem({
        ByLanguageScreen(catalog)
    })
    data class Podcasts(val catalog: Catalog) : TabItem({
        PodcastScreen(catalog)
    })
}

@Composable
fun StationScreen(catalog: Catalog, onStationChosen: OnOutlineChosen) {
    DataScreen(catalog = catalog) {
        StationListContent(details = catalog.detailItems!!) {
            onStationChosen(it)
        }
    }
}

@Composable
fun MusicScreen(catalog: Catalog) {
    DataScreen(catalog = catalog) {
        OutlineLinkListContent(details = catalog.detailItems!!) {
            //TODO add navigation action
        }
    }
}

@Composable
fun TalkScreen(catalog: Catalog) {
    DataScreen(catalog = catalog) {
        OutlineLinkListContent(details = catalog.detailItems!!) {
            //TODO add navigation action
        }
    }
}

@Composable
fun SportsScreen(catalog: Catalog) {
    DataScreen(catalog = catalog) {
        OutlineLinkListContent(details = catalog.detailItems!!) {
            //TODO add navigation action
        }
    }
}

@Composable
fun ByLocationScreen(catalog: Catalog) {
    DataScreen(catalog = catalog) {
        OutlineLinkListContent(details = catalog.detailItems!!) {
            //TODO add navigation action
        }
    }
}

@Composable
fun ByLanguageScreen(catalog: Catalog) {
    DataScreen(catalog = catalog) {
        OutlineLinkListContent(details = catalog.detailItems!!) {
            //TODO add navigation action
        }
    }
}

@Composable
fun PodcastScreen(catalog: Catalog) {
    DataScreen(catalog = catalog) {
        StationListContent(details = catalog.detailItems!!) {
            //TODO add navigation action
        }
    }
}

@Composable
fun DataScreen(catalog: Catalog, screen: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .wrapContentSize(Alignment.Center)
    ) {
        CategoryStateWrapper(catalog) {
            screen()
        }
    }
}

@Composable
fun CategoryStateWrapper(catalog: Catalog, content: @Composable () -> Unit) {
    if (catalog.error != null) {
        ErrorScreen(error = catalog.error!!)
    } else if (catalog.detailItems.isNullOrEmpty() && !catalog.isLoading) {
        //TODO replace for empty screen placeholder
        Text(
            text = "Empty screen placeholder of ${catalog.text}",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    } else if (!catalog.detailItems.isNullOrEmpty()) {
        content()
    } else {
        LoadingScreen(Color.White)
    }
}