package be.swsb.aoc2021.day15

import be.swsb.aoc2021.common.readLines
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day15Test {
    @Nested
    inner class SolutionTests {
        @Test
        fun `solve 1 for test input returns 40`() {
            assertThat(Day15.solve1("day15/testInput.txt".readLines())).isEqualTo(40)
        }

        @Test
        fun `solve 1 for actual input returns 40`() {
            TODO("Not yet implemented")
        }
    }

    @Nested
    inner class UnitTests {
        @Test
        fun `kotlinlang slack test input`() {
            val input = """
                199111
                199191
                111191
                999991""".trimIndent().split("\n")

            assertThat(Day15.solve1(input)).isEqualTo(13)
        }
    }
}