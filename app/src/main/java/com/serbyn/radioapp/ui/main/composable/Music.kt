package com.serbyn.radioapp.ui.main.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.serbyn.radioapp.R
import com.serbyn.radioapp.ui.main.entity.Outline


@Composable
fun OutlineLinkListContent(details: List<Outline>, onItemClicked: OnOutlineChosen) {
    Column(
        modifier = Modifier
            .padding(vertical = 16.dp, horizontal = 16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlineLinkList(items = details) {
            onItemClicked(it)
        }
    }
}

@Composable
fun OutlineLinkList(items: List<Outline>, onItemClicked: OnOutlineChosen) {
    Text(
        text = "Explore",
        fontWeight = FontWeight.Bold,
        color = Color.White,
        textAlign = TextAlign.Center,
        fontSize = 25.sp
    )
    if (items.isNotEmpty()) {
        LazyColumn {
            items(items.size) { item ->
                OutlineLinkRow(Modifier.clickable {
                    onItemClicked(items[item])
                }, items[item])
            }
        }
    } else {
        Text(
            text = "No music items",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun OutlineLinkRow(modifier: Modifier = Modifier, outline: Outline) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = outline.text,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            maxLines = 1,
            fontSize = 18.sp
        )
    }
}
