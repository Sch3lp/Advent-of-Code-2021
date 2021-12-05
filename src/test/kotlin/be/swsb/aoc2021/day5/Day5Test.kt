package be.swsb.aoc2021.day5

import be.swsb.aoc2021.common.readLines
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day5Test {

    @Test
    fun `test input results in 5 overlapping points`() {
        val input = "day5/testInput.txt".readLines()
        val actual = Day5.solve1(input)

        assertThat(actual).isEqualTo(5)
    }

    @Test
    fun `actual input results in 5147 overlapping points`() {
        val input = "day5/actualInput.txt".readLines()
        val actual = Day5.solve1(input)

        assertThat(actual).isEqualTo(5147)
    }

    @Nested
    inner class DrawLineTest {
        @Test
        fun `can draw horizontal lines`() {
            val actual: List<Point> = "0,9 -> 5,9".drawLine()

            assertThat(actual).containsExactly(
                at(0,9),
                at(1,9),
                at(2,9),
                at(3,9),
                at(4,9),
                at(5,9)
            )
        }

        @Test
        fun `can draw vertical lines`() {
            val actual: List<Point> = "9,0 -> 9,5".drawLine()

            assertThat(actual).containsExactly(
                at(9,0),
                at(9,1),
                at(9,2),
                at(9,3),
                at(9,4),
                at(9,5)
            )
        }

        @OptIn(ExperimentalStdlibApi::class)
        @Test
        fun getOverlappingPoints() {
            val firstLine = "0,9 -> 5,9".drawLine()
            val secondLine = "0,9 -> 2,9".drawLine()
            val somePoints = buildList {
                addAll(firstLine)
                addAll(secondLine)
            }
            val actual: Map<Point, Int> = somePoints.getOverlappingPoints()
            assertThat(actual).containsExactly(
                entry(at(0,9),1),
                entry(at(1,9),1),
                entry(at(2,9),1),
            )
        }
    }
}
