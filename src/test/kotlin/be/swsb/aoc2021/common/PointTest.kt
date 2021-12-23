package be.swsb.aoc2021.common

import be.swsb.aoc2021.common.FoldInstruction.Up
import be.swsb.aoc2021.common.Point.Companion.at
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class PointTest {
    @Test
    fun `fold up`() {
        assertThat(at(1,10).fold(Up(7))).isEqualTo(at(1,4))
        assertThat(at(3,14).fold(Up(7))).isEqualTo(at(3,0))
        assertThat(at(5,8).fold(Up(7))).isEqualTo(at(5,6))
    }

    @Test
    fun `fold left`() {
        assertThat(at(10,1).fold(FoldInstruction.Left(7))).isEqualTo(at(4,1))
        assertThat(at(14,3).fold(FoldInstruction.Left(7))).isEqualTo(at(0,3))
        assertThat(at(8,5).fold(FoldInstruction.Left(7))).isEqualTo(at(6,5))
    }
}