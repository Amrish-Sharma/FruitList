package com.cb.fruitlist.ui

import android.speech.tts.TextToSpeech
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cb.fruitlist.R
import java.util.Locale
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.asPaddingValues

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
        onDispose { tts.shutdown() }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF9C4))
            .padding(WindowInsets.systemBars.asPaddingValues())
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
    modifier: Modifier = Modifier,
    colorIndex: Int = 0 // New parameter for color cycling
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (pressed) 1.08f else 1f, label = "scale")
    val dynamicFontSize = (screenWidth / 14).sp
    // Define a palette of toddler-safe, vibrant colors
    val cardColors = listOf(
        Color(0xFFFFF176), // Yellow
        Color(0xFF81C784), // Green
        Color(0xFF64B5F6), // Blue
        Color(0xFFFF8A65), // Orange
        Color(0xFFBA68C8), // Purple
        Color(0xFFFFB74D), // Light Orange
        Color(0xFFAED581), // Light Green
        Color(0xFF4DD0E1), // Cyan
        Color(0xFFE57373), // Red
        Color(0xFFFFD54F)  // Gold
    )
    val cardColor = cardColors[colorIndex % cardColors.size]
    Card(
        modifier = modifier
            .padding(12.dp)
            .fillMaxHeight()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        pressed = true
                        tryAwaitRelease()
                        pressed = false
                        onClick()
                    }
                )
            },
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = label,
                modifier = Modifier
                    .size(80.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                fontSize = dynamicFontSize,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
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
            contentPadding = PaddingValues(0.dp)
        ) {
            itemsIndexed(items) { index, item ->
                CategoryCell(
                    iconRes = item.imageRes,
                    label = item.text,
                    onClick = {
                        tts.speak(item.text, TextToSpeech.QUEUE_FLUSH, null, null)
                        onItemClick(item)
                    },
                    colorIndex = index,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize()
                        .aspectRatio(1f)
                )
            }
        }
    }
}


data class ListItemData(val imageRes: Int, val text: String)
