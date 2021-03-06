package be.swsb.aoc2021.day5

import be.swsb.aoc2021.common.Point
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

    @Test
    fun `test input for part 2 results in 5 overlapping points`() {
        val input = "day5/testInput.txt".readLines()
        val actual = Day5.solve2(input)

        assertThat(actual).isEqualTo(12)
    }

    @Test
    fun `actual input for part 2 results in 16925 overlapping points`() {
        val input = "day5/actualInput.txt".readLines()
        val actual = Day5.solve2(input)

        assertThat(actual).isEqualTo(16925)
    }

    @Nested
    inner class DrawLineTest {
        @Test
        fun `can draw horizontal lines`() {
            val actual: List<Point> = "0,9 -> 5,9".drawLine { startCoord, endCoord -> startCoord.isOrthogonalTo(endCoord) }

            assertThat(actual).containsExactly(
                Point.at(0, 9),
                Point.at(1, 9),
                Point.at(2, 9),
                Point.at(3, 9),
                Point.at(4, 9),
                Point.at(5, 9)
            )
        }

        @Test
        fun `can draw vertical lines`() {
            val actual: List<Point> = "9,0 -> 9,5".drawLine { startCoord, endCoord -> startCoord.isOrthogonalTo(endCoord) }

            assertThat(actual).containsExactly(
                Point.at(9, 0),
                Point.at(9, 1),
                Point.at(9, 2),
                Point.at(9, 3),
                Point.at(9, 4),
                Point.at(9, 5)
            )
        }

        @Test
        fun `can draw diagonal lines`() {
            val actual: List<Point> = "9,7 -> 7,9".drawLine { startCoord, endCoord -> startCoord.isStrict45DiagonalTo(
                endCoord
            ) }

            assertThat(actual).containsExactlyInAnyOrder(
                Point.at(9, 7),
                Point.at(8, 8),
                Point.at(7, 9),
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
                entry(Point.at(0, 9), 1),
                entry(Point.at(1, 9), 1),
                entry(Point.at(2, 9), 1),
            )
        }
    }
}
