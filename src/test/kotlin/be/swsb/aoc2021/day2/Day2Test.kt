package be.swsb.aoc2021.day2

import be.swsb.aoc2021.common.readLines
import be.swsb.aoc2021.day2.Aim.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.math.absoluteValue

class Day2Test {

    @Nested
    @Disabled("will be replaced by Part2")
    inner class Part1Tests {
        @Test
        fun `example input should return 150`() {
            val actual: UInt = Day2.solve("day2/testInput.txt".readLines())

            assertThat(actual).isEqualTo(150u)
        }

        @Test
        fun `actual input should return we don't know yet`() {
            val actual: UInt = Day2.solve("day2/actualInput.txt".readLines())

            assertThat(actual).isEqualTo(150u)
        }
    }

    @Nested
    inner class Part2Tests {
        @Test
        fun `example input should return 150`() {
            val actual: UInt = Day2.solve("day2/testInput.txt".readLines())

            assertThat(actual).isEqualTo(900u)
        }

        @Test
        fun `actual input should return we don't know yet`() {
            val actual: UInt = Day2.solve("day2/actualInput.txt".readLines())

            assertThat(actual).isEqualTo(1845455714u)
        }
    }
}

class SubmarineTest {
    @Test
    fun `when command forwards, moves forwards X units`() {
        aSubmarine().isEqualTo(aSubmarine(horizontal = 0u, depth = 0u, aim = Level))
            .accept("forward 4").isEqualTo(aSubmarine(horizontal = 4u, depth = 0u, aim = Level))
            .accept("forward 3").isEqualTo(aSubmarine(horizontal = 7u, depth = 0u, aim = Level))
    }

    @Test
    fun `when command forwards 2 while aiming down 10, moves forwards and dives further down`() {
        aSubmarine(aim = Down(10u))
            .accept("forward 2").isEqualTo(aSubmarine(horizontal = 2u, depth = 20u, aim = Down(10u)))
            .accept("forward 3").isEqualTo(aSubmarine(horizontal = 5u, depth = 50u, aim = Down(10u)))
    }

    @Test
    fun `when command down, aims the submarine down X units`() {
        aSubmarine().isEqualTo(aSubmarine(horizontal = 0u, depth = 0u))
            .accept("down 7").isEqualTo(aSubmarine(horizontal = 0u, depth = 0u, aim = Down(7u)))
            .accept("down 8").isEqualTo(aSubmarine(horizontal = 0u, depth = 0u, aim = Down(15u)))
            .accept("forward 2").isEqualTo(aSubmarine(horizontal = 2u, depth = 30u, aim = Down(15u)))
    }

    @Test
    fun `when command up, aims the submarine up X units`() {
        aSubmarine(horizontal = 0u, depth = 28u)
            .accept("up 4").isEqualTo(aSubmarine(horizontal = 0u, depth = 28u, aim = Up(4u)))
            .accept("up 3").isEqualTo(aSubmarine(horizontal = 0u, depth = 28u, aim = Up(7u)))
            .accept("forward 2").isEqualTo(aSubmarine(horizontal = 2u, depth = 14u, aim = Up(7u)))
    }

    @Test
    fun `a submarine doesn't turn into an aerosub when it submerges`() {
        aSubmarine()
            .accept(listOf("up 1", "forward 5")).isEqualTo(aSubmarine(horizontal = 5u, depth = 0u, aim = Up(1u)))
    }

    @Test
    fun `accepting up and down makes the sub level again`() {
        aSubmarine()
            .accept(listOf("up 1", "down 1")).isEqualTo(aSubmarine(horizontal = 0u, depth = 0u, aim = Level))
            .accept(listOf("down 1", "up 1")).isEqualTo(aSubmarine(horizontal = 0u, depth = 0u, aim = Level))
    }

    @Test
    fun `when accepting multiple commands, the sub executes them in order`() {
        aSubmarine()
            .accept("forward 5").isEqualTo(aSubmarine(horizontal = 5u, depth = 0u, aim = Level))
            .accept("down 5").isEqualTo(aSubmarine(horizontal = 5u, depth = 0u, aim = Down(5u)))
            .accept("forward 8").isEqualTo(aSubmarine(horizontal = 13u, depth = 8u * 5u, aim = Down(5u)))
            .accept("up 3").isEqualTo(aSubmarine(horizontal = 13u, depth = 40u, aim = Down(2u)))
            .accept("down 8").isEqualTo(aSubmarine(horizontal = 13u, depth = 40u, aim = Down(10u)))
            .accept("forward 2").isEqualTo(aSubmarine(horizontal = 15u, depth = 40u + (2u * 10u), aim = Down(10u)))
    }
}

fun Submarine.isEqualTo(expectedSub: Submarine) =
    also { assertThat(this).isEqualTo(expectedSub) }

object Day2 {
    fun solve(commandsAsStrings: List<String>): UInt {
        return aSubmarine().accept(commandsAsStrings).asSolution()
    }
}