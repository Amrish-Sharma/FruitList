package com.cb.fruitlist

import com.cb.fruitlist.data.CategoryData
import com.cb.fruitlist.quiz.buildQuiz
import com.cb.fruitlist.ui.ListItemData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.random.Random

class QuizEngineTest {
    private val items = (1..8).map { ListItemData(it, "Item $it") }

    @Test
    fun buildsRequestedNumberOfRounds() {
        assertEquals(5, buildQuiz(items, random = Random(1)).size)
    }

    @Test
    fun eachQuestionHasFourDistinctOptionsIncludingTarget() {
        buildQuiz(items, random = Random(2)).forEach { q ->
            assertEquals(4, q.options.size)
            assertEquals(4, q.options.toSet().size)
            assertTrue(q.target in q.options)
        }
    }

    @Test
    fun targetsDoNotRepeat() {
        val targets = buildQuiz(items, random = Random(3)).map { it.target }
        assertEquals(targets.size, targets.toSet().size)
    }

    @Test
    fun mixedCategoryCombinesAllCategories() {
        val mixed = CategoryData.itemsFor(CategoryData.MIXED)
        assertEquals(CategoryData.categories.sumOf { CategoryData.itemsFor(it).size }, mixed.size)
        assertEquals(48, mixed.map { it.text }.toSet().size)
    }

    @Test
    fun smallCategoryLimitsRoundsAndChoices() {
        val quiz = buildQuiz(items.take(2), random = Random(4))
        assertEquals(2, quiz.size)
        quiz.forEach { assertEquals(2, it.options.size) }
    }
}
