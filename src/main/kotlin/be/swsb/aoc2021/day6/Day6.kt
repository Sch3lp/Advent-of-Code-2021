package be.swsb.aoc2021.day6

object Day6 {
    fun solve1(input: List<Int>, days: Int): Int {
        return dayCycle(input, days).size
    }
    fun solve2(input: List<String>): Int {
        return 0
    }
}

fun dayCycle(school: List<Int>, days: Int): List<Int> {
    return (1..days).fold(school) { acc, day -> acc.dayPasses() }
}

fun List<Int>.dayPasses(): List<Int> {
    val amountOfNewSpawns = count { it == 0 }
    val newSpawns: Sequence<Int> = generateSequence { 8 }.take(amountOfNewSpawns)
    return map { if (it == 0) 6 else it - 1 } + newSpawns.toList()
}