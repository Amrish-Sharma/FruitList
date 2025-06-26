package com.cb.fruitlist.ui

import android.speech.tts.TextToSpeech
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cb.fruitlist.R
import java.util.Locale

@Composable
fun MainScreen(onCategoryClick: (String) -> Unit) {
    val context = LocalContext.current
    val tts = remember {
        var ttsInstance: TextToSpeech? = null
        ttsInstance = TextToSpeech(context) { ttsEngineStatus ->
            if (ttsEngineStatus == TextToSpeech.SUCCESS) {
                val ttsLocale = Locale("en", "IN")
                ttsInstance?.language = ttsLocale
            }
        }
        ttsInstance
    }
    DisposableEffect(Unit) {
        onDispose { tts?.shutdown() }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF9C4))
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CategoryCell(iconRes = R.drawable.fruit_icon, label = "Fruit", onClick = {
                tts.speak("Fruit", TextToSpeech.QUEUE_FLUSH, null, null)
                onCategoryClick("Fruit")
            }, modifier = Modifier.weight(1f))
            CategoryCell(iconRes = R.drawable.vegetable_icon, label = "Vegetable", onClick = {
                tts.speak("Vegetable", TextToSpeech.QUEUE_FLUSH, null, null)
                onCategoryClick("Vegetable")
            }, modifier = Modifier.weight(1f))
        }
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CategoryCell(iconRes = R.drawable.animal_icon, label = "Animal", onClick = {
                tts.speak("Animal", TextToSpeech.QUEUE_FLUSH, null, null)
                onCategoryClick("Animal")
            }, modifier = Modifier.weight(1f))
            CategoryCell(iconRes = R.drawable.vehicle_icon, label = "Vehicle", onClick = {
                tts.speak("Vehicle", TextToSpeech.QUEUE_FLUSH, null, null)
                onCategoryClick("Vehicle")
            }, modifier = Modifier.weight(1f))
        }
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CategoryCell(iconRes = R.drawable.bird_icon, label = "Bird", onClick = {
                tts.speak("Bird", TextToSpeech.QUEUE_FLUSH, null, null)
                onCategoryClick("Bird")
            }, modifier = Modifier.weight(1f))
            CategoryCell(iconRes = R.drawable.flower_icon, label = "Flower", onClick = {
                tts.speak("Flower", TextToSpeech.QUEUE_FLUSH, null, null)
                onCategoryClick("Flower")
            }, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun CategoryCell(
    iconRes: Int,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (pressed) 1.08f else 1f, label = "scale")
    Card(
        modifier = modifier
            .padding(12.dp)
            .fillMaxHeight()
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        pressed = true
                        tryAwaitRelease()
                        pressed = false
                        onClick()
                    }
                )
            }
            .scale(scale),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.85f)
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFB3E5FC), // light blue
                            Color(0xFFE1BEE7)  // light purple
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(8.dp)
            ) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = label,
                    modifier = Modifier.size(96.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = label,
                    fontSize = 28.sp, // Keep increased size for readability
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    color = Color(0xFF1976D2),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun ListScreen(items: List<ListItemData>, onItemClick: (ListItemData) -> Unit = {}) {
    val context = LocalContext.current
    val tts = remember {
        var ttsInstance: TextToSpeech? = null
        ttsInstance = TextToSpeech(context) { ttsEngineStatus ->
            if (ttsEngineStatus == TextToSpeech.SUCCESS) {
                val ttsLocale = Locale("en", "IN")
                ttsInstance?.language = ttsLocale
            }
        }
        ttsInstance
    }
    DisposableEffect(Unit) {
        onDispose { tts.shutdown() }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF9C4))
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(0.dp) // Remove padding to cover the whole screen
        ) {
            items(items) { item ->
                CategoryCell(
                    iconRes = item.imageRes,
                    label = item.text,
                    onClick = {
                        tts.speak(item.text, TextToSpeech.QUEUE_FLUSH, null, null)
                        onItemClick(item)
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize() // Ensure each cell fills its grid space
                        .aspectRatio(1f)
                )
            }
        }
    }
}


data class ListItemData(val imageRes: Int, val text: String)
