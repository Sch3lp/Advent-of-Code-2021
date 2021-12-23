package be.swsb.aoc2021.day13

import be.swsb.aoc2021.common.FoldInstruction
import be.swsb.aoc2021.common.Point
import be.swsb.aoc2021.common.Point.Companion.at

object Day13 {
    fun solve1(input: List<String>): Int {
        val (paper, instructions) = parse(input)
        return paper.fold(instructions.take(1)).visibleDots
    }

    fun solve2(input: List<String>): String {
        val (paper, instructions) = parse(input)
        val foldedPaper = paper.fold(instructions)
        return foldedPaper.asString()
    }

    fun parse(input: List<String>): Pair<Paper, List<FoldInstruction>> {
        val foldInstructions = input.subList(input.indexOf("") + 1, input.size)
        val paper = input.subList(0, input.indexOf(""))
        return Paper(paper.map { Point.fromString(it) }.toSet()) to foldInstructions.map { it.parseToFold() }
    }
}

data class Paper(private val dots: Set<Point>) {

    val visibleDots: Int by lazy { dots.size }
    private val dimension: Pair<Int, Int> = dots.maxOf { it.x } to dots.maxOf { it.y }

    init {
        println("Paper has dimension of ${dimension.first} by ${dimension.second}")
    }

    fun fold(instructions: List<FoldInstruction>): Paper =
        instructions.fold(this) { acc, instruction -> acc.fold(instruction) }

    fun fold(instruction: FoldInstruction) = instruction.run {
        println("Folding $this. Middle axes: x ${dimension.first / 2}, y ${dimension.second / 2}")
        Paper(dots.map { it.fold(this) }.toSet())
    }

    fun asString(): String {
        return (0..dimension.second).joinToString("\n") { y ->
            (0..dimension.first).joinToString("") { x ->
                if (at(x,y) in dots) {
                    "◻️"
                } else {
                    "◼️"
                }
            }
        }
    }
}

private fun String.parseToFold() = when {
    contains("fold along y") -> FoldInstruction.Up(findInt())
    contains("fold along x") -> FoldInstruction.Left(findInt())
    else -> throw IllegalArgumentException("Could not parse $this into a FoldInstruction")
}

private fun String.findInt() = """\d+""".toRegex().find(this)?.value?.toInt()
    ?: throw IllegalArgumentException("Could not find a crease value in a fold along statement.")