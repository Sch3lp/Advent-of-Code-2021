package be.swsb.aoc2021.day10

import be.swsb.aoc2021.day10.SyntaxError.AutoCompletion
import be.swsb.aoc2021.day10.SyntaxError.Corruption

object Day10 {
    fun solve1(input: List<String>): Int {
        return input.mapNotNull { it.compile() }.filterIsInstance<Corruption>()
            .sumOf { corruption -> corruption.penaltyPoints }
    }

    fun solve2(input: List<String>): Long {
        val autoCompletionPoints = input.mapNotNull { it.compile() }
            .filterIsInstance<AutoCompletion>()
            .map { it.points }
            .sorted()
        return autoCompletionPoints[autoCompletionPoints.size / 2]
    }
}


fun String.compile(): SyntaxError? {
    val openings = "({[<"
    val closings = ")}]>"
    val expected = mutableListOf<Char>()
    forEach { c ->
        if (c in openings) {
            expected += closings[openings.indexOf(c)]
        } else {
            if (c != expected.last()) {
                return Corruption(expected.last(), c)
            } else {
                expected.removeLast()
            }
        }
    }
    return if (expected.isNotEmpty()) {
        AutoCompletion(expected.joinToString("").reversed())
    } else {
        null
    }
}

sealed class SyntaxError {
    data class Corruption(val expected: Char, val actual: Char) : SyntaxError() {
        val penaltyPoints: Int
            get() = when (actual) {
                ')' -> 3
                ']' -> 57
                '}' -> 1197
                '>' -> 25137
                else -> 0
            }
    }

    data class AutoCompletion(val missingClosings: String) : SyntaxError() {
        val points: Long
            get() = missingClosings.fold(0) { acc, c -> (acc * 5) + pointsTable[c]!! }

        private val pointsTable = mapOf(
            ')' to 1,
            ']' to 2,
            '}' to 3,
            '>' to 4,
        )
    }
}
