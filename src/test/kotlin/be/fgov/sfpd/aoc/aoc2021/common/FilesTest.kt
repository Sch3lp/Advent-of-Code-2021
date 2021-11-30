package be.fgov.sfpd.aoc.aoc2021.common

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FilesTest {

    @Test
    fun `can read from file`() {
        val actual: List<String> = "input.txt".asFile().readLines()

        assertThat(actual).containsExactly(
            "1,Bruce,Wayne",
            "2,Edward,Nigma"
        )
    }

    @Test
    fun `made up problem, printing the names from the people in the file`() {
        val lines: List<String> = "input.txt".asFile().readLines()
        val actual: String = solve1(lines)

        assertThat(actual).isEqualTo(
            """
                Bruce Wayne
                Edward Nigma
            """.trimIndent()
        )
    }

    
}

private fun solve1(inputLines: List<String>): String =
    inputLines.map { line ->
        val (firstname, lastname) = line.split(',').drop(1)
        Pair(firstname, lastname)
    }.joinToString(separator = "\n") { pair -> "${pair.first} ${pair.second}" }


