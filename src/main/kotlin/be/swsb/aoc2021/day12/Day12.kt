package be.swsb.aoc2021.day12

object Day12 {
    fun solve1(input: List<String>): Int {
        return from(input).findAllPaths().size
    }

    fun solve2(input: List<String>): Int {
        return from(input).findAllPaths2().size
    }

    fun from(input: String): Graph = from(input.split("\n"))
    fun from(inputAsEdges: List<String>): Graph {
        val edges = inputAsEdges.map { edgeAsString ->
            val (from, to) = edgeAsString.split("-")
            from to to
        }
        return Graph(edges)
    }
}