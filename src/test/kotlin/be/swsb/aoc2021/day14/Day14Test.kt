package be.swsb.aoc2021.day14

import be.swsb.aoc2021.common.readLines
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day14Test {
    @Nested
    inner class SolutionTests {
        @Test
        fun `part 1's solution for test input is 1588`() {
            val actual = Day14.solve1("day14/testInput.txt".readLines())
            assertThat(actual).isEqualTo(1749 - 161)
        }

        @Test
        fun `part 1's solution for actual input is 2223`() {
            val actual = Day14.solve1("day14/actualInput.txt".readLines())
            assertThat(actual).isEqualTo(2223)
        }

        @Test
        fun `part 2's solution for test input is 2188189693529`() {
            val actual = Day14.solve2("day14/testInput.txt".readLines())
            assertThat(actual).isEqualTo(2192039569602 - 3849876073)
        }

        @Test
        fun `part 2's solution for actual input is larger than 2223`() {
            val actual = Day14.solve2("day14/actualInput.txt".readLines())
            assertThat(actual).isEqualTo(2223)
        }
    }

    @Nested
    inner class UnitTests {
        @Test
        fun `can parse into template and rules`() {
            val (template, rules) = Day14.parse("day14/testInput.txt".readLines())

            assertThat(template).isEqualTo("NNCB")
            assertThat(rules).containsExactlyInAnyOrder(
                Rule('C' with 'H', 'B'),
                Rule('H' with 'H', 'N'),
                Rule('C' with 'B', 'H'),
                Rule('N' with 'H', 'C'),
                Rule('H' with 'B', 'C'),
                Rule('H' with 'C', 'B'),
                Rule('H' with 'N', 'C'),
                Rule('N' with 'N', 'C'),
                Rule('B' with 'H', 'H'),
                Rule('N' with 'C', 'B'),
                Rule('N' with 'B', 'B'),
                Rule('B' with 'N', 'B'),
                Rule('B' with 'B', 'N'),
                Rule('B' with 'C', 'B'),
                Rule('C' with 'C', 'N'),
                Rule('C' with 'N', 'C'),
            )
        }

        @Test
        fun `can expand polymer by injecting according to rules`() {
            val (_, rules) = Day14.parse("day14/testInput.txt".readLines())
            "NNCB".expandWith(rules).also { expandedPolymer -> assertThat(expandedPolymer).isEqualTo("NCNBCHB") }
                .expandWith(rules).also { expandedPolymer -> assertThat(expandedPolymer).isEqualTo("NBCCNBBBCBHCB") }
                .expandWith(rules)
                .also { expandedPolymer -> assertThat(expandedPolymer).isEqualTo("NBBBCNCCNBBNBNBBCHBHHBCHB") }
                .expandWith(rules)
                .also { expandedPolymer -> assertThat(expandedPolymer).isEqualTo("NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB") }
        }
    }
}

private fun String.expandWith(rules: List<Rule>): String = Day14.expandPolymer(this, rules)
private fun String.asCombination(): Combination = Combination(this.first(), this.last())