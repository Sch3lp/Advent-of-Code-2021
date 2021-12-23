package be.swsb.aoc2021.day13

import be.swsb.aoc2021.common.Point
import be.swsb.aoc2021.common.Point.Companion.at
import be.swsb.aoc2021.common.readLines
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day13Test {

    @Nested
    inner class SolutionTests {
        @Test
        fun `solve1 with testInput should return 17`() {
            val actual = Day13.solve1("day13/testInput.txt".readLines())
            assertThat(actual).isEqualTo(17)
        }

        @Test
        fun `solve1 with actualInput should return 2`() {
            val actual = Day13.solve1("day13/actualInput.txt".readLines())
            assertThat(actual).isEqualTo(2)
        }
    }

    @Nested
    inner class ParseTests {
        @Test
        fun `can parse into a Paper and fold instructions`() {
            val (paper, foldInstructions) = parse("day13/testInput.txt".readLines())

            assertThat(foldInstructions).containsExactlyInAnyOrder(FoldInstruction.Up(7), FoldInstruction.Left(5))
            assertThat(paper).isEqualTo(
                Paper(
                    listOf(
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
    }
}


fun parse(input: List<String>): Pair<Paper, List<FoldInstruction>> {
    val foldInstructions = input.subList(input.indexOf("") + 1, input.size)
    val paper = input.subList(0, input.indexOf(""))
    return Paper(paper.map { Point.fromString(it) }) to foldInstructions.map(FoldInstruction::fromString)
}

data class Paper(private val dots: List<Point>)
sealed interface FoldInstruction {
    data class Up(private val across: Int) : FoldInstruction {
        val crease = at(0, across)
    }

    data class Left(private val across: Int) : FoldInstruction {
        val crease = at(across, 0)
    }

    companion object {
        fun fromString(value: String) = when {
            value.contains("fold along y") -> Up(value.findInt())
            value.contains("fold along x") -> Left(value.findInt())
            else -> throw IllegalArgumentException("Could not parse $value into a FoldInstruction")
        }
    }
}

fun String.findInt() = """\d""".toRegex().find(this)?.value?.toInt() ?: throw IllegalArgumentException("Could not find a crease value in a fold along statement.")