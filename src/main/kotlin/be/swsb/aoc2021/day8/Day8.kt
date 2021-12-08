package be.swsb.aoc2021.day8

private const val amountOfSignalsToDisplay1 = 2
private const val amountOfSignalsToDisplay4 = 4
private const val amountOfSignalsToDisplay7 = 3
private const val amountOfSignalsToDisplay8 = 7

object Day8 {

    fun solve1(input: List<String>): Int {
        return input.sumOf { line ->
            val digits: List<String> = line.substringAfter("| ").split(" ")
            digits.count {
                it.length in listOf(
                    amountOfSignalsToDisplay1,
                    amountOfSignalsToDisplay4,
                    amountOfSignalsToDisplay7,
                    amountOfSignalsToDisplay8
                )
            }
        }
    }

    fun solve2(input: List<String>): Int {
        TODO()
    }
}