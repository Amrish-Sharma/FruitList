package com.cb.fruitlist.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cb.fruitlist.R


@Composable
fun MainScreen(onCategoryClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CategorySection(iconRes = R.drawable.fruit_icon, label = "Fruit" ,onClick = { onCategoryClick("Fruit") })
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 1.dp,
            color = Color.Black
        )
        CategorySection(iconRes = R.drawable.vegetable_icon, label = "Vegetable", onClick = { onCategoryClick("Vegetable") })
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 1.dp,
            color = Color.Black
        )
        CategorySection(iconRes = R.drawable.animal_icon, label = "Animal", onClick = { onCategoryClick("Animal") })
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 1.dp,
            color = Color.Black
        )
        CategorySection(iconRes = R.drawable.vehicle_icon, label = "Vehicle", onClick = { onCategoryClick("Vehicle") })
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 1.dp,
            color = Color.Black
        )
        CategorySection(iconRes = R.drawable.bird_icon, label = "Bird", onClick = { onCategoryClick("Bird") })
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 1.dp,
            color = Color.Black
        )
        CategorySection(iconRes = R.drawable.flower_icon, label = "Flower", onClick = { onCategoryClick("Flower") })
    }
}

@Composable
fun CategorySection(iconRes: Int, label: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .wrapContentWidth()
            .padding(bottom = 16.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            modifier = Modifier.size(120.dp) // Increased from 64.dp to 120.dp
        )
        Text(
            text = label,
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun ListScreen(items: List<ListItemData>, onItemClick: (ListItemData) -> Unit = {}) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(items) { item ->
            ListRowItem(item = item, onClick = { onItemClick(item) })
        }
    }
}

@Composable
fun ListRowItem(item: ListItemData, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = item.imageRes),
            contentDescription = item.text,
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = item.text,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            color = Color(0xFF808080),
            modifier = Modifier.padding(top = 40.dp, end = 20.dp)
        )
    }
}

data class ListItemData(val imageRes: Int, val text: String)
