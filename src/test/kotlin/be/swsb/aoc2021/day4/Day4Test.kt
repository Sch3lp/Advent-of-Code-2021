package be.swsb.aoc2021.day4

import be.swsb.aoc2021.common.readLines
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

private val firstBingoBoardInput = """
22 13 17 11  0
 8  2 23  4 24
21  9 14 16  7
 6 10  3 18  5
 1 12 20 15 19
            """.trimIndent().split("\n")

class Day4Test {

    @Test
    fun `Example input should return 4512`() {
        val input = "day4/testInput.txt".readLines()

        assertThat(Day4.solve1(input)).isEqualTo(4512)
    }

    @Test
    fun `Actual input should return 4512`() {
        val input = "day4/actualInput.txt".readLines()

        assertThat(Day4.solve1(input)).isEqualTo(25023)
    }

    @Nested
    inner class BingBoardTest {
        @Test
        fun `Can parse a Bingo board from a 5x5 string table`() {
            val actual: BingoBoard = parseToBingoBoard(firstBingoBoardInput)

            assertThat(actual.rows).containsExactly(
                Row(22, 13, 17, 11, 0),
                Row(8, 2, 23, 4, 24),
                Row(21, 9, 14, 16, 7),
                Row(6, 10, 3, 18, 5),
                Row(1, 12, 20, 15, 19),
            )
            assertThat(actual.columns).containsExactly(
                Column(22, 8, 21, 6, 1),
                Column(13, 2, 9, 10, 12),
                Column(17, 23, 14, 3, 20),
                Column(11, 4, 16, 18, 15),
                Column(0, 24, 7, 5, 19),
            )
        }

        @Test
        fun `marking a number that does exist, marks the matching number on the BingoBoard`() {
            val startingBingoBoard: BingoBoard = parseToBingoBoard(firstBingoBoardInput)

            startingBingoBoard.findAndMark(24)

            assertThat(startingBingoBoard.find(24)).isEqualTo(MarkableNumber(24, true))
        }

        @Test
        fun `there's a bingo when a BingoBoard has a completely marked row`() {
            val startingBingoBoard: BingoBoard = parseToBingoBoard(firstBingoBoardInput)

            assertThat(startingBingoBoard.findAndMark(22)).isFalse()
            assertThat(startingBingoBoard.findAndMark(13)).isFalse()
            assertThat(startingBingoBoard.findAndMark(17)).isFalse()
            assertThat(startingBingoBoard.findAndMark(11)).isFalse()
            assertThat(startingBingoBoard.findAndMark(0)).isTrue()
        }

        @Test
        fun `there's a bingo when a BingoBoard has a completely marked column`() {
            val startingBingoBoard: BingoBoard = parseToBingoBoard(firstBingoBoardInput)

            assertThat(startingBingoBoard.findAndMark(22)).isFalse()
            assertThat(startingBingoBoard.findAndMark(8)).isFalse()
            assertThat(startingBingoBoard.findAndMark(21)).isFalse()
            assertThat(startingBingoBoard.findAndMark(6)).isFalse()
            assertThat(startingBingoBoard.findAndMark(1)).isTrue()
        }
    }
}