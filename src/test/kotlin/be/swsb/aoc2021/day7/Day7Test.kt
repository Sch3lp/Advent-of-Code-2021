package be.swsb.aoc2021.day7

import be.swsb.aoc2021.Day7
import be.swsb.aoc2021.Fuel
import be.swsb.aoc2021.common.readLines
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day7Test {
    @Test
    fun `solve1 with testInput returns 37`() {
        val actual: Fuel = Day7.solve1("day7/testInput.txt".readLines()[0].split(",").map{ it.toInt()})
        assertThat(actual).isEqualTo(37)
    }
    
    @Test
    fun `solve1 with actualInput returns something`() {
        val actual: Fuel = Day7.solve1("day7/actualInput.txt".readLines()[0].split(",").map{ it.toInt()})
        assertThat(actual).isEqualTo(37)
    }
}
