package be.swsb.aoc2021.day5

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
    val (startCoord, endCoord) = replace(" ", "").split("->").take(2).map { pointFromString(it) }

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

data class Point(val x: Int, val y: Int) {
    operator fun rangeTo(other: Point): List<Point> {
        val diagonalVector = determineVectorTo(other)
        var cur = this
        val points = mutableListOf(cur)
        while (cur != other) {
            cur += diagonalVector
            points.add(cur)
        }
        return points
    }

    fun isOrthogonalTo(other: Point) =
        isVerticalTo(other) || isHorizontalTo(other)

    private fun isVerticalTo(other: Point) =
        this.x == other.x

    private fun isHorizontalTo(other: Point) =
        this.y == other.y

    fun isStrict45DiagonalTo(other: Point) =
        this.x != other.x && this.y != other.y

    private fun determineVectorTo(other: Point): Point {
        val x = when {
            this.x < other.x -> 1
            this.x > other.x -> -1
            else -> 0
        }
        val y = when {
            this.y < other.y -> 1
            this.y > other.y -> -1
            else -> 0
        }
        return Point(x, y)
    }

    private operator fun plus(other: Point) = Point(this.x + other.x, this.y + other.y)
}

fun at(x: Int, y: Int): Point = Point(x, y)
fun pointFromString(input: String): Point {
    val (x, y) = input.split(",").map { it.toInt() }
    return Point(x, y)
}