package be.swsb.aoc2021.day13

import be.swsb.aoc2021.common.FoldInstruction
import be.swsb.aoc2021.common.Point.Companion.at
import be.swsb.aoc2021.common.readLines
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day13Test {

    @Nested
    inner class SolutionTests {
        @Test
        fun `solve1 with testInput should return 17 visible dots`() {
            val actual = Day13.solve1("day13/testInput.txt".readLines())
            assertThat(actual).isEqualTo(17)
        }

        @Test
        fun `solve1 with actualInput should return 731 visible dots`() {
            val actual = Day13.solve1("day13/actualInput.txt".readLines())
            assertThat(actual).isEqualTo(731)
        }
    }

    @Nested
    inner class ParseTests {
        @Test
        fun `can parse into a Paper and fold instructions`() {
            val (paper, foldInstructions) = Day13.parse("day13/testInput.txt".readLines())

            assertThat(foldInstructions).containsExactlyInAnyOrder(FoldInstruction.Up(7), FoldInstruction.Left(5))
            assertThat(paper).isEqualTo(
                Paper(
                    setOf(
                        at(6, 10),
                        at(0, 14),
                        at(9, 10),
                        at(0, 3),
                        at(10, 4),
                        at(4, 11),
                        at(6, 0),
                        at(6, 12),
                        at(4, 1),
                        at(0, 13),
                        at(10, 12),
                        at(3, 4),
                        at(3, 0),
                        at(8, 4),
                        at(1, 10),
                        at(2, 14),
                        at(8, 10),
                        at(9, 0)
                    )
                )
            )
        }

        @Test
        fun `can fold`() {
            val (paper, _) = Day13.parse("day13/testInput.txt".readLines())

            paper.fold(FoldInstruction.Up(7)).also {
                assertThat(it).isEqualTo(
                    Paper(
                        setOf(
                            at(6, 4),
                            at(0, 0),
                            at(9, 4),
                            at(0, 3),
                            at(10, 4),
                            at(4, 3),
                            at(6, 0),
                            at(6, 2),
                            at(4, 1),
                            at(0, 1),
                            at(10, 2),
                            at(3, 4),
                            at(3, 0),
                            at(1, 4),
                            at(2, 0),
                            at(8, 4),
                            at(9, 0)
                        )
                    )
                )
            }.fold(FoldInstruction.Left(5)).also {
                assertThat(it).isEqualTo(
                    Paper(
                        setOf(
                            at(4, 4),
                            at(0, 0),
                            at(1, 4),
                            at(0, 3),
                            at(0, 4),
                            at(4, 3),
                            at(4, 0),
                            at(4, 2),
                            at(4, 1),
                            at(0, 1),
                            at(0, 2),
                            at(3, 4),
                            at(3, 0),
                            at(2, 0),
                            at(2, 4),
                            at(1, 0)
                        )
                    )
                )
            }
        }
    }
}