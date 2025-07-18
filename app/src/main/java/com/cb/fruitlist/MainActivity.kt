package com.cb.fruitlist

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.cb.fruitlist.ui.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Use the new recommended API for edge-to-edge
        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    MainScreen { categoryName ->
                        val intent = Intent(this, CategoryActivity::class.java)
                        intent.putExtra("category_name", categoryName)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}
