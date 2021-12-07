package be.swsb.aoc2021.day6

import be.swsb.aoc2021.common.readLines
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day6Test {

    @Test
    fun `Part 1's test input should result in 5934`() {
        val input = "day6/testInput.txt".readLines().flatMap { line -> line.split(",").map { it.toInt() } }
        val after18Days = Day6.solve1(input, 18)
        assertThat(after18Days).isEqualTo(26)

        val actual = Day6.solve1(input, 80)
        assertThat(actual).isEqualTo(5934)
    }

    @Test
    fun `Part 1's actual input should result in 456`() {
        val input = "day6/actualInput.txt".readLines().flatMap { line -> line.split(",").map { it.toInt() } }
        val actual = Day6.solve1(input, 80)
        assertThat(actual).isEqualTo(390923)
    }

    @Nested
    inner class SomeUnitTests {
        @Test
        fun `Given an lanternfish at 1, when the day passes twice, one new lanternfish at 8 spawns, and the other lanternfish is reset to 6`() {
            listOf(1)
                .dayPasses()
                .also { assertThat(it).containsExactly(0) }
                .dayPasses()
                .also{ assertThat(it).containsExactly(6, 8) }

            val actual = dayCycle(listOf(1),2)
            assertThat(actual).containsExactly(6, 8)
        }
    }
}