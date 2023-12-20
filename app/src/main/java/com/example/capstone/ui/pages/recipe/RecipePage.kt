package com.example.capstone.ui.pages.recipe

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.capstone.R
import com.example.capstone.data.Dummy
import com.example.capstone.ui.component.JetCard
import com.example.capstone.ui.theme.CapstoneTheme

@Composable
fun RecipePage() {
    Image(
        painter = painterResource(id = R.drawable.corner),
        contentDescription = null,
        modifier = Modifier.size(100.dp)
    )
    Column(
        modifier = Modifier.padding(5.dp, top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.listpage),
                contentDescription = null,
                modifier = Modifier
                    .size(250.dp)
                    .padding(bottom = 15.dp)
                    .wrapContentSize(Alignment.TopCenter)
            )
        }
        lazyGrid()
    }
}

@Composable
fun lazyGrid(){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(5.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(Dummy.dummys, key = {it.id}) { item ->
            JetCard(image = item.photo, titile = item.name, date = item.date)
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
fun RecipePagepPreview(){
    CapstoneTheme() {
        RecipePage()
    }
}