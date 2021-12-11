package be.swsb.aoc2021.day7

import be.swsb.aoc2021.*
import be.swsb.aoc2021.common.readLines
import be.swsb.aoc2021.common.softly
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Test
import kotlin.math.abs
import kotlin.math.roundToInt

class Day7Test {
    @Test
    fun `solve1 with testInput returns 37`() {
        val actual: Fuel = Day7.solve1("day7/testInput.txt".readLines()[0].split(",").map { it.toInt() })

        assertThat(actual).isEqualTo(37)
    }

    @Test
    fun `solve1 with actualInput returns 352254`() {
        val actual: Fuel = Day7.solve1("day7/actualInput.txt".readLines()[0].split(",").map { it.toInt() })
        assertThat(actual).isEqualTo(352254)
    }

    @Test
    fun `solve2 with testInput returns 168`() {
        val actual: Fuel = Day7.solve2("day7/testInput.txt".readLines()[0].split(",").map { it.toInt() })

        assertThat(actual).isEqualTo(168)
    }

    @Test
    fun `solve2 with actualInput returns 99053143`() {
        val actual: Fuel = Day7.solve2("day7/actualInput.txt".readLines()[0].split(",").map { it.toInt() })
        assertThat(actual).isEqualTo(99053143)
    }

    @Test
    fun `fuel expenditure`() {
        softly {
            assertThat(calculateFuelSpent(1, 5)).isEqualTo(10)
            assertThat(calculateFuelSpent(7, 5)).isEqualTo(3)
            assertThat(calculateFuelSpent(14, 5)).isEqualTo(45)
            assertThat(calculateFuelSpent(16, 1)).isEqualTo(120)

            assertThat(calculateFuelSpent(16, 8)).isEqualTo(36)
            assertThat(calculateFuelSpent(1, 8)).isEqualTo(28)
            assertThat(calculateFuelSpent(16, 3)).isEqualTo(91)
            assertThat(calculateFuelSpent(16, 4)).isEqualTo(78)
            assertThat(calculateFuelSpent(1, 4)).isEqualTo(6) //78 + 30 = 108
            assertThat(calculateFuelSpent(1, 3)).isEqualTo(3) //91 + 15 = 106
            assertThat(listOf(1, 1, 1, 1, 1, 16).average()).isEqualTo(3.5)
        }
    }
}