package be.swsb.aoc2021.day12

object Day12 {
    fun solve1(input: List<String>): Int {
        return Graph.from(input).findAllPaths("start","end").size
    }

    fun solve2(input: List<String>): Int {
        return 0
    }
}