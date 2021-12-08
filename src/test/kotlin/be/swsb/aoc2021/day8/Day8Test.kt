package be.swsb.aoc2021.day8

import be.swsb.aoc2021.common.readLines
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class Day8Test {

    @Test
    fun `solve1 with test input returns 26`() {
        val actual = Day8.solve1("day8/testInput.txt".readLines())
        assertThat(actual).isEqualTo(26)
    }

    @Test
    fun `solve1 with actual input returns 349`() {
        val actual = Day8.solve1("day8/actualInput.txt".readLines())
        assertThat(actual).isEqualTo(349)
    }
}