package be.swsb.aoc2021.day10

import be.swsb.aoc2021.common.readLines
import be.swsb.aoc2021.common.softly
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day10Test {
    @Nested
    inner class Solutions {
        @Test
        fun `Solving part 1 given test input should return 26397`() {
            assertThat(Day10.solve1("day10/testInput.txt".readLines())).isEqualTo(26397)
        }

        @Test
        fun `Solving part 1 given actual input should return 296535`() {
            assertThat(Day10.solve1("day10/actualInput.txt".readLines())).isEqualTo(296535)
        }

        @Test
        fun `Solving part 2 given test input should return 26397`() {
            assertThat(Day10.solve2("day10/testInput.txt".readLines())).isEqualTo(26397)
        }

        @Test
        fun `Solving part 2 given actual input should return 26397`() {
            assertThat(Day10.solve2("day10/actualInput.txt".readLines())).isEqualTo(26397)
        }
    }

    @Nested
    inner class UnitTests {
        @Test
        fun `can compile to errors`() {
            softly {
                assertThat("{([(<{}[<>[]}>{[]{[(<()>".compile()).isEqualTo(Corruption(expected = ']', actual = '}'))
                assertThat("[[<[([]))<([[{}[[()]]]".compile()).isEqualTo(Corruption(expected = ']', actual = ')'))
                assertThat("[{[{({}]{}}([{[{{{}}([]".compile()).isEqualTo(Corruption(expected = ')', actual = ']'))
                assertThat("[<(<(<(<{}))><([]([]()".compile()).isEqualTo(Corruption(expected = '>', actual = ')'))
                assertThat("<{([([[(<>()){}]>(<<{{".compile()).isEqualTo(Corruption(expected = ']', actual = '>'))
            }
        }

        @Test
        fun `can compile without errors`() {
            softly {
                assertThat(("{([(<{}[<>[]]>{[]{[(<()>)]}})])}").compile()).isNull()
            }
        }
    }

}
