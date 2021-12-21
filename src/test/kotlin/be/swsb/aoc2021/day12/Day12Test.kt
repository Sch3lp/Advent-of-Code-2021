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
            val actual: List<String> = graph.findAllPaths2().map { path -> path.joinToString(",") }
            assertThat(actual)
                .hasSize(10)
                .containsExactlyInAnyOrder(
                    "start,A,b,A,c,A,end",
                    "start,A,b,A,end",
                    "start,A,b,end",
                    "start,A,c,A,b,A,end",
                    "start,A,c,A,b,end",
                    "start,A,c,A,end",
                    "start,A,end",
                    "start,b,A,c,A,end",
                    "start,b,A,end",
                    "start,b,end",
                )
        }

        @Test
        fun `first example - part 2`() {
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
            val actual: List<String> = graph.findAllPaths2().map { path -> path.joinToString(",") }
            assertThat(actual)
                .hasSize(36)
                .containsExactlyInAnyOrder(
                    "start,A,b,A,b,A,c,A,end",
                    "start,A,b,A,b,A,end",
                    "start,A,b,A,b,end",
                    "start,A,b,A,c,A,b,A,end",
                    "start,A,b,A,c,A,b,end",
                    "start,A,b,A,c,A,c,A,end",
                    "start,A,b,A,c,A,end",
                    "start,A,b,A,end",
                    "start,A,b,d,b,A,c,A,end",
                    "start,A,b,d,b,A,end",
                    "start,A,b,d,b,end",
                    "start,A,b,end",
                    "start,A,c,A,b,A,b,A,end",
                    "start,A,c,A,b,A,b,end",
                    "start,A,c,A,b,A,c,A,end",
                    "start,A,c,A,b,A,end",
                    "start,A,c,A,b,d,b,A,end",
                    "start,A,c,A,b,d,b,end",
                    "start,A,c,A,b,end",
                    "start,A,c,A,c,A,b,A,end",
                    "start,A,c,A,c,A,b,end",
                    "start,A,c,A,c,A,end",
                    "start,A,c,A,end",
                    "start,A,end",
                    "start,b,A,b,A,c,A,end",
                    "start,b,A,b,A,end",
                    "start,b,A,b,end",
                    "start,b,A,c,A,b,A,end",
                    "start,b,A,c,A,b,end",
                    "start,b,A,c,A,c,A,end",
                    "start,b,A,c,A,end",
                    "start,b,A,end",
                    "start,b,d,b,A,c,A,end",
                    "start,b,d,b,A,end",
                    "start,b,d,b,end",
                    "start,b,end",
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
            val actual: List<String> = graph.findAllPaths().map { path -> path.joinToString(",") }
            assertThat(actual)
                .hasSize(19)
                .containsExactlyInAnyOrder(
                    "start,HN,dc,HN,end",
                    "start,HN,dc,HN,kj,HN,end",
                    "start,HN,dc,end",
                    "start,HN,dc,kj,HN,end",
                    "start,HN,end",
                    "start,HN,kj,HN,dc,HN,end",
                    "start,HN,kj,HN,dc,end",
                    "start,HN,kj,HN,end",
                    "start,HN,kj,dc,HN,end",
                    "start,HN,kj,dc,end",
                    "start,dc,HN,end",
                    "start,dc,HN,kj,HN,end",
                    "start,dc,end",
                    "start,dc,kj,HN,end",
                    "start,kj,HN,dc,HN,end",
                    "start,kj,HN,dc,end",
                    "start,kj,HN,end",
                    "start,kj,dc,HN,end",
                    "start,kj,dc,end",
                )
        }
    }
}