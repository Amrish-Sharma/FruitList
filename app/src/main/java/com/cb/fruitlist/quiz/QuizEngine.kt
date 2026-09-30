package com.cb.fruitlist.quiz

import com.cb.fruitlist.ui.ListItemData
import kotlin.random.Random

data class QuizQuestion(val target: ListItemData, val options: List<ListItemData>)

fun buildQuiz(
    items: List<ListItemData>,
    rounds: Int = 5,
    choices: Int = 4,
    random: Random = Random.Default
): List<QuizQuestion> {
    val distinct = items.distinctBy { it.text }
    val optionCount = choices.coerceAtMost(distinct.size)
    return distinct.shuffled(random).take(rounds).map { target ->
        val distractors = (distinct - target).shuffled(random).take(optionCount - 1)
        QuizQuestion(target, (distractors + target).shuffled(random))
    }
}
