package be.swsb.aoc2021.day1

import be.swsb.aoc2021.common.readLines
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day1Test {

    @Nested
    inner class Puzzle1Tests {

        @Test
        fun `example input should return 7 increases`() {
            val readLines: List<String> = "day1/testInput.txt".readLines()

            assertThat(solve1(readLines)).isEqualTo(7)
        }

        @Test
        fun `actual input should return the answer`() {
            val readLines: List<String> = "day1/actualInput.txt".readLines()

            assertThat(solve1(readLines)).isEqualTo(1791)
        }
    }

    @Nested
    inner class Puzzle2Tests {

        @Test
        fun `example input should return 5 increases`() {
            val readLines: List<String> = "day1/testInput.txt".readLines()

            assertThat(solve2(readLines)).isEqualTo(5)
        }

        @Test
        fun `actual input should return the answer`() {
            val readLines: List<String> = "day1/actualInput.txt".readLines()

            assertThat(solve2(readLines)).isEqualTo(1822)
        }
    }
}
