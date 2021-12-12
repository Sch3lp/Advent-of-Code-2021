package be.swsb.aoc2021.day9

import be.swsb.aoc2021.common.readLines
import be.swsb.aoc2021.common.softly
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


class Day9Test {
    @Nested
    inner class Solutions {
        @Test
        fun `Solving part 1 given test input should return 15`() {
            assertThat(Day9.solve1("day9/testInput.txt".readLines())).isEqualTo(15)
        }

        @Test
        fun `Solving part 1 given actual input should return 462`() {
            assertThat(Day9.solve1("day9/actualInput.txt".readLines())).isEqualTo(462)
        }

        @Test
        fun `Solving part 2 given test input should return 1134`() {
            assertThat(Day9.solve2("day9/testInput.txt".readLines())).isEqualTo(1134)
        }

        @Test
        fun `Solving part 2 given actual input should return 1397760`() {
            assertThat(Day9.solve2("day9/actualInput.txt".readLines()))
                .isGreaterThan(936000)
                .isLessThan(  1961856)
                .isEqualTo(1397760)
        }
    }

    @Nested
    inner class UnitTests {
        @Test
        fun `We can transform a list of strings to a heightmap of a cave floor`() {
            val input = "day9/testInput.txt".readLines()

            val caveFloor = input.toHeightmap()

            //@formatter:off
            assertThat(caveFloor.tiles).containsExactly(
                heightAt(0,0,2),heightAt(1,0,1),heightAt(2,0,9),heightAt(3,0,9),heightAt(4,0,9),heightAt(5,0,4),heightAt(6,0,3),heightAt(7,0,2),heightAt(8,0,1),heightAt(9,0,0),
                heightAt(0,1,3),heightAt(1,1,9),heightAt(2,1,8),heightAt(3,1,7),heightAt(4,1,8),heightAt(5,1,9),heightAt(6,1,4),heightAt(7,1,9),heightAt(8,1,2),heightAt(9,1,1),
                heightAt(0,2,9),heightAt(1,2,8),heightAt(2,2,5),heightAt(3,2,6),heightAt(4,2,7),heightAt(5,2,8),heightAt(6,2,9),heightAt(7,2,8),heightAt(8,2,9),heightAt(9,2,2),
                heightAt(0,3,8),heightAt(1,3,7),heightAt(2,3,6),heightAt(3,3,7),heightAt(4,3,8),heightAt(5,3,9),heightAt(6,3,6),heightAt(7,3,7),heightAt(8,3,8),heightAt(9,3,9),
                heightAt(0,4,9),heightAt(1,4,8),heightAt(2,4,9),heightAt(3,4,9),heightAt(4,4,9),heightAt(5,4,6),heightAt(6,4,5),heightAt(7,4,6),heightAt(8,4,7),heightAt(9,4,8),
            )
            //@formatter:on
        }

        @Test
        fun `A CaveTile is lowest when it's the lowest height compared to its orthogonal neighbours`() {
            val heightMap = """
                150
                945
                793
            """.trimIndent().split("\n").toHeightmap()

            softly {
                assertThat(heightAt(0,0,1).isLowest(heightMap)).isTrue()
                assertThat(heightAt(2,0,0).isLowest(heightMap)).isTrue()
                assertThat(heightAt(1,1,4).isLowest(heightMap)).isTrue()
                assertThat(heightAt(0,2,7).isLowest(heightMap)).isTrue()
                assertThat(heightAt(2,2,3).isLowest(heightMap)).isTrue()
                assertThat(heightAt(1,0,5).isLowest(heightMap)).isFalse()
                assertThat(heightAt(0,1,9).isLowest(heightMap)).isFalse()
                assertThat(heightAt(2,1,5).isLowest(heightMap)).isFalse()
                assertThat(heightAt(2,1,9).isLowest(heightMap)).isFalse()
            }
        }
    }
}
