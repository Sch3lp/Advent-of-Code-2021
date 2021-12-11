package be.swsb.aoc2021.day5

import be.swsb.aoc2021.common.Point

object Day5 {
    fun solve1(input: List<String>): Int {
        return input.map { it.drawLine { startCoord, endCoord -> startCoord.isOrthogonalTo(endCoord) } }
            .flatten()
            .getOverlappingPoints()
            .size
    }

    fun solve2(input: List<String>): Int {
        return input.map {
            it.drawLine { startCoord, endCoord ->
                startCoord.isOrthogonalTo(endCoord) || startCoord.isStrict45DiagonalTo(endCoord)
            }
        }
            .flatten()
            .getOverlappingPoints()
            .size
    }
}

fun List<Point>.getOverlappingPoints(): Map<Point, Int> {
    return this.groupBy { it }.mapValues { it.value.size - 1 }.filterValues { it > 0 }
}

fun String.drawLine(constraint: ((Point, Point) -> Boolean)? = null): List<Point> {
    val (startCoord, endCoord) = replace(" ", "").split("->").take(2).map { Point.fromString(it) }

    return if (constraint != null) {
        if (constraint(startCoord, endCoord)) {
            startCoord..endCoord
        } else {
            emptyList()
        }
    } else {
        startCoord..endCoord
    }
}