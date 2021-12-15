package be.swsb.aoc2021.day11

import be.swsb.aoc2021.common.readLines
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day11Test {
    @Nested
    inner class Solutions {
        @Test
        fun `Solving part 1 given test input should return 1656`() {
            assertThat(Day11.solve1("day11/testInput.txt".readLines())).isEqualTo(1656u)
        }

        @Test
        fun `Solving part 1 given actual input should return 1656`() {
            assertThat(Day11.solve1("day11/actualInput.txt".readLines())).isEqualTo(1688u)
        }

        @Test
        fun `Solving part 2 given test input should return 195`() {
            assertThat(Day11.solve2("day11/testInput.txt".readLines())).isEqualTo(195)
        }

        @Test
        fun `Solving part 2 given actual input should return 403`() {
            assertThat(Day11.solve2("day11/actualInput.txt".readLines())).isEqualTo(403)
        }
    }

    @Nested
    inner class UnitTests {
        @Test
        fun `one low energy octopus surrounded by only full energy octopusses also ends up flashing`() {
            val consortium = DumboOctopusConsortium(
                """
                11111
                19991
                19191
                19991
                11111
            """.trimIndent()
            )

            val afterFirstStep = consortium.step()
            assertThat(afterFirstStep.asString()).isEqualToIgnoringWhitespace(
                """Amount of Flashed Octopi: 9 
                34543
                40004
                50005
                40004
                34543
            """.trimIndent()
            )

            val afterSecondStep = afterFirstStep.step()
            assertThat(afterSecondStep.asString()).isEqualToIgnoringWhitespace(
                """Amount of Flashed Octopi: 9
                45654
                51115
                61116
                51115
                45654
            """.trimIndent()
            )
        }
    }
}
