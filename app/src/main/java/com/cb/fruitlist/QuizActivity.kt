package com.cb.fruitlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.cb.fruitlist.data.CategoryData
import com.cb.fruitlist.ui.QuizScreen

class QuizActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val categoryName = intent.getStringExtra("category_name") ?: "Category"
        val items = CategoryData.itemsFor(categoryName)
        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    QuizScreen(items, onBack = { finish() })
                }
            }
        }
    }
}
