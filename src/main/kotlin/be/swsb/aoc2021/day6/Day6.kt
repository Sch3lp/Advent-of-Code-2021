package be.swsb.aoc2021.day6

object Day6 {

    fun solve1(input: List<Int>, days: Int): Int {
        return dayCycle(input, days).size
    }
    fun solve2(input: List<Int>, days: Int): Long {
        return dayCyclePart2(input, days)
    }
}

fun dayCycle(school: List<Int>, days: Int): List<Int> {
    return (1..days).fold(school) { acc, _ -> acc.dayPasses() }
}

fun List<Int>.dayPasses(): List<Int> {
    val amountOfNewSpawns = count { it == 0 }
    val newSpawns: Sequence<Int> = generateSequence { 8 }.take(amountOfNewSpawns)
    return map { if (it == 0) 6 else it - 1 } + newSpawns.toList()
}

fun dayCyclePart2(initialSchool: List<Int>, days: Int): Long {
    val fishGroupedByIncubationTime: Map<IncubationTime, Long> = (0..8).associateWith { incubationTime ->
        initialSchool.count { it == incubationTime }.toLong()
    }

    return (1..days).fold(fishGroupedByIncubationTime) { acc, _ -> acc.dayPasses() }.values.sum()
}

fun Map<IncubationTime, Long>.dayPasses(): Map<IncubationTime, Long> {
    return map { (k, _) ->
        when (k) {
            6 -> k to (this[k+1]!! + this[0]!!)
            8 -> k to this[0]!!
            else -> k to this[k+1]!!
        }
    }.toMap()
}

typealias IncubationTime = Int