package be.swsb.aoc2021.day12

import be.swsb.aoc2021.common.readLines
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day12Test {

    @Nested
    inner class SolutionTests {
        @Test
        fun `solve1 for test input returns 226`() {
            val actual = Day12.solve1("day12/testInput.txt".readLines())
            assertThat(actual).isEqualTo(226)
        }

        @Test
        fun `solve1 for actual input returns 3802`() {
            val actual = Day12.solve1("day12/actualInput.txt".readLines())
            assertThat(actual).isEqualTo(3802)
        }
    }

    @Nested
    inner class UnitTests {
        @Test
        fun `first example`() {
            val input = """
                start-A
                start-b
                A-c
                A-b
                b-d
                A-end
                b-end
            """.trimIndent()

            val graph = Graph.from(input)
            val actual: List<List<String>> = graph.findAllPaths()
            assertThat(actual)
                .hasSize(10)
                .containsExactlyInAnyOrder(
                listOf("start","A","b","A","c","A","end"),
                listOf("start","A","b","A","end"),
                listOf("start","A","b","end"),
                listOf("start","A","c","A","b","A","end"),
                listOf("start","A","c","A","b","end"),
                listOf("start","A","c","A","end"),
                listOf("start","A","end"),
                listOf("start","b","A","c","A","end"),
                listOf("start","b","A","end"),
                listOf("start","b","end"),
            )
        }

        @Test
        fun `slightly larger example`() {
            val input = """
                dc-end
                HN-start
                start-kj
                dc-start
                dc-HN
                LN-dc
                HN-end
                kj-sa
                kj-HN
                kj-dc
            """.trimIndent()

            val graph = Graph.from(input)
            val actual: List<List<String>> = graph.findAllPaths()
            assertThat(actual)
                .hasSize(19)
                .containsExactlyInAnyOrder(
                listOf("start", "HN", "dc", "HN", "end"),
                listOf("start", "HN", "dc", "HN", "kj", "HN", "end"),
                listOf("start", "HN", "dc", "end"),
                listOf("start", "HN", "dc", "kj", "HN", "end"),
                listOf("start", "HN", "end"),
                listOf("start", "HN", "kj", "HN", "dc", "HN", "end"),
                listOf("start", "HN", "kj", "HN", "dc", "end"),
                listOf("start", "HN", "kj", "HN", "end"),
                listOf("start", "HN", "kj", "dc", "HN", "end"),
                listOf("start", "HN", "kj", "dc", "end"),
                listOf("start", "dc", "HN", "end"),
                listOf("start", "dc", "HN", "kj", "HN", "end"),
                listOf("start", "dc", "end"),
                listOf("start", "dc", "kj", "HN", "end"),
                listOf("start", "kj", "HN", "dc", "HN", "end"),
                listOf("start", "kj", "HN", "dc", "end"),
                listOf("start", "kj", "HN", "end"),
                listOf("start", "kj", "dc", "HN", "end"),
                listOf("start", "kj", "dc", "end"),
            )
        }
    }
}