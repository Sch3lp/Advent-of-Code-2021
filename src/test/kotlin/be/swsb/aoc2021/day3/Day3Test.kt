package be.swsb.aoc2021.day3

import be.swsb.aoc2021.common.readLines
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


class Day3Test {
    @Nested
    inner class PowerConsumptionTests {
        @Test
        fun `test input returns the power consumption`() {
            val input = "day3/testInput.txt".readLines()
            val (gammaBit, epsilonBit) = Day3.getPowerConsumption(input)
            val gamma = gammaBit.toDecimal()
            val epsilon = epsilonBit.toDecimal()
            assertThat(gamma).isEqualTo(22)
            assertThat(epsilon).isEqualTo(9)
            assertThat(gamma * epsilon).isEqualTo(198)
        }

        @Test
        fun `actual input returns the power consumption`() {
            val input = "day3/actualInput.txt".readLines()
            val (gammaBit, epsilonBit) = Day3.getPowerConsumption(input)
            val gamma = gammaBit.toDecimal()
            val epsilon = epsilonBit.toDecimal()
            assertThat(gamma * epsilon).isEqualTo(2583164)
        }
    }

    @Nested
    inner class LifeSupportRatingTests {

        @Test
        fun `test input returns the Life Support Rating`() {
            val input = "day3/testInput.txt".readLines()
            val (oxygenRating, co2ScrubberRating) = Day3.getLifeSupportRating(input)

            assertThat(oxygenRating).isEqualTo(23)
            assertThat(co2ScrubberRating).isEqualTo(10)
            assertThat(oxygenRating * co2ScrubberRating).isEqualTo(230)
        }

        @Test
        fun `actual input returns the Life Support Rating`() {
            val input = "day3/actualInput.txt".readLines()
            val (oxygenRating, co2ScrubberRating) = Day3.getLifeSupportRating(input)

            assertThat(oxygenRating).isEqualTo(825)
            assertThat(co2ScrubberRating).isEqualTo(3375)
            assertThat(oxygenRating * co2ScrubberRating).isEqualTo(2784375)
        }
    }
}