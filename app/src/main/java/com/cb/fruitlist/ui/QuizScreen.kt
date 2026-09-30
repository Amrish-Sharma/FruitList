package com.cb.fruitlist.ui

import android.speech.tts.TextToSpeech
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cb.fruitlist.quiz.QuizQuestion
import com.cb.fruitlist.quiz.buildQuiz
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

private val QuizBackground = Color(0xFFFFF9C4)
private val StarGold = Color(0xFFFFC107)
private val StarEmpty = Color(0xFFBDBDBD)

@Composable
fun QuizScreen(items: List<ListItemData>, onBack: () -> Unit) {
    var quiz by remember { mutableStateOf(buildQuiz(items)) }
    var session by remember { mutableStateOf(0) }
    var index by remember { mutableStateOf(0) }
    var stars by remember { mutableStateOf(0) }
    var ttsReady by remember { mutableStateOf(false) }
    val tts = rememberTts(onReady = { ttsReady = true })
    val speak: (String) -> Unit = { tts.speak(it, TextToSpeech.QUEUE_FLUSH, null, null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(QuizBackground)
            .padding(WindowInsets.systemBars.asPaddingValues())
    ) {
        if (index >= quiz.size) {
            key(session) {
                QuizResult(
                    stars = stars,
                    total = quiz.size,
                    speak = speak,
                    onPlayAgain = {
                        quiz = buildQuiz(items)
                        index = 0
                        stars = 0
                        session++
                    },
                    onBack = onBack
                )
            }
        } else {
            key(session, index) {
                QuizQuestionView(
                    question = quiz[index],
                    stars = stars,
                    total = quiz.size,
                    ttsReady = ttsReady,
                    speak = speak,
                    onAnswered = { firstTry ->
                        if (firstTry) stars++
                        index++
                    }
                )
            }
        }
    }
}

@Composable
private fun QuizQuestionView(
    question: QuizQuestion,
    stars: Int,
    total: Int,
    ttsReady: Boolean,
    speak: (String) -> Unit,
    onAnswered: (firstTry: Boolean) -> Unit
) {
    val prompt = "Find the ${question.target.text}!"
    var wrongPicks by remember { mutableStateOf(setOf<String>()) }
    var solved by remember { mutableStateOf(false) }

    LaunchedEffect(ttsReady) {
        if (ttsReady) speak(prompt)
    }
    LaunchedEffect(solved) {
        if (solved) {
            delay(1800)
            onAnswered(wrongPicks.isEmpty())
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StarRow(filled = stars, total = total)
                Button(
                    onClick = { speak(prompt) },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF64B5F6))
                ) {
                    Text(text = "🔊", fontSize = 32.sp)
                }
            }
            Text(
                text = prompt,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF5D4037),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            question.options.chunked(2).forEachIndexed { rowIndex, rowItems ->
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                ) {
                    rowItems.forEachIndexed { colIndex, item ->
                        val isTarget = item == question.target
                        QuizOptionCard(
                            item = item,
                            colorIndex = rowIndex * 2 + colIndex,
                            showLabel = solved && isTarget,
                            dimmed = item.text in wrongPicks || (solved && !isTarget),
                            isTarget = isTarget,
                            onPick = pick@{
                                if (solved) return@pick false
                                if (isTarget) {
                                    solved = true
                                    speak("Yay! ${item.text}!")
                                } else {
                                    wrongPicks = wrongPicks + item.text
                                    speak("Oops! That is ${item.text}. $prompt")
                                }
                                true
                            }
                        )
                    }
                }
            }
        }
        if (solved) {
            ConfettiBurst(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
private fun RowScope.QuizOptionCard(
    item: ListItemData,
    colorIndex: Int,
    showLabel: Boolean,
    dimmed: Boolean,
    isTarget: Boolean,
    onPick: () -> Boolean
) {
    val scope = rememberCoroutineScope()
    val shake = remember { Animatable(0f) }
    val bounce = remember { Animatable(1f) }
    CategoryCell(
        iconRes = item.imageRes,
        label = item.text,
        colorIndex = colorIndex,
        showLabel = showLabel,
        onClick = {
            if (onPick()) {
                scope.launch {
                    if (isTarget) {
                        bounce.animateTo(1.2f, spring(stiffness = Spring.StiffnessMedium))
                        bounce.animateTo(
                            1f,
                            spring(dampingRatio = Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessLow)
                        )
                    } else {
                        for (offset in listOf(-30f, 30f, -20f, 20f, -10f, 0f)) {
                            shake.animateTo(offset, tween(durationMillis = 50))
                        }
                    }
                }
            }
        },
        modifier = Modifier
            .weight(1f)
            .graphicsLayer {
                translationX = shake.value
                scaleX = bounce.value
                scaleY = bounce.value
                alpha = if (dimmed) 0.4f else 1f
            }
    )
}

@Composable
private fun StarRow(filled: Int, total: Int, fontSize: Int = 40) {
    Row {
        repeat(total) { i ->
            Text(
                text = "★",
                fontSize = fontSize.sp,
                color = if (i < filled) StarGold else StarEmpty
            )
        }
    }
}

@Composable
private fun QuizResult(
    stars: Int,
    total: Int,
    speak: (String) -> Unit,
    onPlayAgain: () -> Unit,
    onBack: () -> Unit
) {
    val starScale = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        val cheer = if (stars == total) "Wow! Perfect!" else "Great job!"
        speak("$cheer You got $stars ${if (stars == 1) "star" else "stars"}!")
        starScale.animateTo(
            1f,
            spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow)
        )
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "★",
                fontSize = 180.sp,
                color = StarGold,
                modifier = Modifier.graphicsLayer {
                    scaleX = starScale.value
                    scaleY = starScale.value
                }
            )
            Text(
                text = "You got $stars ${if (stars == 1) "star" else "stars"}!",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF5D4037),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            StarRow(filled = stars, total = total, fontSize = 36)
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = onPlayAgain,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF81C784)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
            ) {
                Text(text = "🔁 Play Again", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8A65)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
            ) {
                Text(text = "🏠 Back", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }
        }
        if (stars > 0) {
            ConfettiBurst(modifier = Modifier.fillMaxSize())
        }
    }
}

private class ConfettiParticle(
    val angle: Float,
    val speed: Float,
    val radius: Float,
    val color: Color
)

@Composable
private fun ConfettiBurst(modifier: Modifier = Modifier) {
    val colors = listOf(
        Color(0xFFE57373), Color(0xFF64B5F6), Color(0xFF81C784),
        Color(0xFFFFD54F), Color(0xFFBA68C8), Color(0xFFFF8A65)
    )
    val particles = remember {
        List(70) {
            ConfettiParticle(
                angle = Random.nextFloat() * 2f * PI.toFloat(),
                speed = 0.3f + Random.nextFloat() * 0.7f,
                radius = 8f + Random.nextFloat() * 10f,
                color = colors.random()
            )
        }
    }
    val progress = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        progress.animateTo(1f, tween(durationMillis = 1500, easing = LinearEasing))
    }
    Canvas(modifier = modifier) {
        val t = progress.value
        if (t >= 1f) return@Canvas
        val reach = size.minDimension * 0.6f
        particles.forEach { p ->
            val distance = p.speed * reach * t
            val gravity = size.height * 0.35f * t * t
            drawCircle(
                color = p.color.copy(alpha = 1f - t),
                radius = p.radius,
                center = Offset(
                    center.x + cos(p.angle) * distance,
                    center.y + sin(p.angle) * distance + gravity
                )
            )
        }
    }
}
