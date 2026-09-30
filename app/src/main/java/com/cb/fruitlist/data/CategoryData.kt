package com.cb.fruitlist.data

import com.cb.fruitlist.R
import com.cb.fruitlist.ui.ListItemData

object CategoryData {
    const val MIXED = "Mixed"
    val categories = listOf("Fruit", "Vegetable", "Animal", "Vehicle", "Bird", "Flower")

    fun itemsFor(category: String): List<ListItemData> = when (category) {
        MIXED -> categories.flatMap { itemsFor(it) }
        "Fruit" -> listOf(
            ListItemData(R.drawable.apple, "Apple"),
            ListItemData(R.drawable.mango, "Mango"),
            ListItemData(R.drawable.banana, "Banana"),
            ListItemData(R.drawable.orange, "Orange"),
            ListItemData(R.drawable.guava, "Guava"),
            ListItemData(R.drawable.grape, "Grapes"),
            ListItemData(R.drawable.pineapple, "Pineapple"),
            ListItemData(R.drawable.strawberry, "Strawberry"),
        )
        "Vegetable" -> listOf(
            ListItemData(R.drawable.potato, "Potato"),
            ListItemData(R.drawable.carrot, "Carrot"),
            ListItemData(R.drawable.broccoli, "Broccoli"),
            ListItemData(R.drawable.tomato, "Tomato"),
            ListItemData(R.drawable.spinach, "Spinach"),
            ListItemData(R.drawable.cucumber, "Cucumber"),
            ListItemData(R.drawable.onion, "Onion"),
            ListItemData(R.drawable.pumpkin, "Pumpkin")
        )
        "Animal" -> listOf(
            ListItemData(R.drawable.dog, "Dog"),
            ListItemData(R.drawable.cat, "Cat"),
            ListItemData(R.drawable.elephant, "Elephant"),
            ListItemData(R.drawable.lion, "Lion"),
            ListItemData(R.drawable.monkey, "Monkey"),
            ListItemData(R.drawable.panda, "Panda"),
            ListItemData(R.drawable.tiger, "Tiger"),
            ListItemData(R.drawable.zebra, "Zebra")
        )
        "Vehicle" -> listOf(
            ListItemData(R.drawable.cycle, "Cycle"),
            ListItemData(R.drawable.motorcycle, "Motorcycle"),
            ListItemData(R.drawable.car, "Car"),
            ListItemData(R.drawable.bus, "Bus"),
            ListItemData(R.drawable.truck, "Truck"),
            ListItemData(R.drawable.plane, "Aeroplane"),
            ListItemData(R.drawable.boat, "Boat"),
            ListItemData(R.drawable.train, "Train")
        )
        "Bird" -> listOf(
            ListItemData(R.drawable.parrot, "Parrot"),
            ListItemData(R.drawable.crow, "Crow"),
            ListItemData(R.drawable.ostrich, "Ostrich"),
            ListItemData(R.drawable.pigeon, "Pigeon"),
            ListItemData(R.drawable.kiwi, "Kiwi"),
            ListItemData(R.drawable.peacock, "Peacock"),
            ListItemData(R.drawable.sparrow, "Sparrow"),
            ListItemData(R.drawable.eagle, "Eagle")
        )
        "Flower" -> listOf(
            ListItemData(R.drawable.rose, "Rose"),
            ListItemData(R.drawable.lily, "Lily"),
            ListItemData(R.drawable.hibiscus, "Hibiscus"),
            ListItemData(R.drawable.marigold, "Marigold"),
            ListItemData(R.drawable.sunflower, "Sunflower"),
            ListItemData(R.drawable.jasmine, "Jasmine"),
            ListItemData(R.drawable.tulip, "Tulip"),
            ListItemData(R.drawable.daisy, "Daisy")
        )
        else -> listOf(
            ListItemData(R.drawable.fruit_icon, "Item 1"),
            ListItemData(R.drawable.fruit_icon, "Item 2")
        )
    }
}
