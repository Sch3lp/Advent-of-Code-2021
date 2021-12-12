package be.swsb.aoc2021.day10

object Day10 {
    fun solve1(input: List<String>): Int {
        return input.mapNotNull { it.compile() }.sumOf { corruption -> corruption.penaltyPoints }
    }

    fun solve2(input: List<String>): Int {
        return 0
    }
}


fun String.compile(): Corruption? {
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
    return null
}

data class Corruption(val expected: Char, val actual: Char) {
    val penaltyPoints: Int
        get() = when (actual) {
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            '>' -> 25137
            else -> 0
        }
}