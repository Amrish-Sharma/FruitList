package com.cb.fruitlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.cb.fruitlist.ui.ListItemData
import com.cb.fruitlist.ui.ListScreen

class CategoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val categoryName = intent.getStringExtra("category_name") ?: "Category"
        val items = when (categoryName) {
            "Fruit" -> listOf(
                ListItemData(R.drawable.mango, "Mango"),
                ListItemData(R.drawable.banana, "Banana"),
                ListItemData(R.drawable.orange, "Orange"),
                ListItemData(R.drawable.guava, "Guava"),
                ListItemData(R.drawable.grape, "Grapes"),
                ListItemData(R.drawable.watermelon, "Watermelon"),
            )
            "Vegetable" -> listOf(
                ListItemData(R.drawable.carrot, "Carrot"),
                ListItemData(R.drawable.broccoli, "Broccoli"),
                ListItemData(R.drawable.tomato, "Tomato"),
                ListItemData(R.drawable.spinach, "Spinach"),
                ListItemData(R.drawable.cucumber, "Cucumber"),
                ListItemData(R.drawable.potato, "Potato")

            )
            "Animal" -> listOf(
                ListItemData(R.drawable.dog, "Dog"),
                ListItemData(R.drawable.cat, "Cat"),
                ListItemData(R.drawable.elephant, "Elephant"),
                ListItemData(R.drawable.lion, "Lion"),
                ListItemData(R.drawable.tiger, "Tiger"),
                ListItemData(R.drawable.zebra, "Zebra")
            )
            "Vehicle" -> listOf(
                ListItemData(R.drawable.cycle, "Cycle"),
                ListItemData(R.drawable.motorcycle, "Motorcycle"),
                ListItemData(R.drawable.car, "Car"),
                ListItemData(R.drawable.bus, "Bus"),
                ListItemData(R.drawable.truck, "Truck"),
                ListItemData(R.drawable.plane, "Aeroplane")

            )
            "Bird" -> listOf(
                ListItemData(R.drawable.parrot, "Parrot"),
                ListItemData(R.drawable.crow, "Crow"),
                ListItemData(R.drawable.ostrich, "Ostrich"),
                ListItemData(R.drawable.pigeon, "Pigeon"),
                ListItemData(R.drawable.kiwi, "Kiwi"),
                ListItemData(R.drawable.peacock, "Peacock")

            )
            "Flower" -> listOf(
                ListItemData(R.drawable.rose, "Rose"),
                ListItemData(R.drawable.lily, "Lily"),
                ListItemData(R.drawable.hibiscus, "Hibiscus"),
                ListItemData(R.drawable.marigold, "Marigold"),
                ListItemData(R.drawable.sunflower, "Sunflower"),
                ListItemData(R.drawable.jasmine, "Jasmine")
            )
            else -> listOf(
                ListItemData(R.drawable.fruit_icon, "Item 1"),
                ListItemData(R.drawable.fruit_icon, "Item 2")
            )
        }
        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    ListScreen(items)
                }
            }
        }
    }
}
