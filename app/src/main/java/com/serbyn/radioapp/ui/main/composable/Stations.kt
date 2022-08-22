package com.serbyn.radioapp.ui.main.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.serbyn.radioapp.R
import com.serbyn.radioapp.ui.main.entity.Outline

@Composable
fun StationListContent(details: List<Outline>, onItemClicked: OnOutlineChosen) {
    Column(
        modifier = Modifier
            .padding(vertical = 16.dp, horizontal = 16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (station in details) {
            StationList(station) {
                onItemClicked(it)
            }
        }
    }
}

@Composable
fun StationList(item: Outline, onItemClicked: OnOutlineChosen) {
    Text(
        text = item.text,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        textAlign = TextAlign.Center,
        fontSize = 25.sp
    )
    val children = item.children
    if (!children.isNullOrEmpty()) {
        LazyColumn {
            items(children.size) { item ->
                StationRow(Modifier.clickable {
                    onItemClicked(children[item])
                }, children[item])
            }
        }
    } else {
        Text(
            text = "No stations",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun StationRow(modifier: Modifier = Modifier, outline: Outline) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(outline.image)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier.size(60.dp),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.placeholder)
        )
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.0f),
                text = outline.text,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                maxLines = 1,
                fontSize = 18.sp
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.0f),
                text = outline.subtext ?: "",
                fontWeight = FontWeight.Light,
                color = Color.LightGray,
                maxLines = 1,
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
        }
    }
}
