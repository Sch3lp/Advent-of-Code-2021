package be.swsb.aoc2021.day5

object Day5 {
    fun solve1(input: List<String>) : Int {
        return input.map { it.drawLine() }
            .flatten()
            .getOverlappingPoints()
            .size
    }
}

fun List<Point>.getOverlappingPoints(): Map<Point, Int> {
    return this.groupBy{ it }.mapValues { it.value.size - 1 }.filterValues { it > 0 }
}

fun String.drawLine() : List<Point> {
    val (startCoord, endCoord) = replace(" ", "").split("->").take(2).map { pointFromString(it) }
    return if (startCoord.x == endCoord.x || startCoord.y == endCoord.y) {
         startCoord..endCoord
    } else {
        emptyList()
    }
}


data class Point(val x: Int, val y: Int) {
    operator fun rangeTo(other: Point): List<Point> {
        val (minX, maxX) = listOf(x,other.x).sorted()
        val (minY, maxY) = listOf(y,other.y).sorted()
        val xRange = minX..maxX
        val yRange = minY..maxY
        return xRange.flatMap { x->
            yRange.map { y ->
                Point(x,y)
            }
        }
    }
}

fun at(x: Int, y: Int): Point = Point(x,y)
fun pointFromString(input: String): Point {
    val (x,y) = input.split(",").map { it.toInt() }
    return Point(x, y)
}