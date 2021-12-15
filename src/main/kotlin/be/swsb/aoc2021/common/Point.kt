package be.swsb.aoc2021.common


data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point) = Point(this.x + other.x, this.y + other.y)

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

    fun isStrict45DiagonalTo(other: Point) =
        this.x != other.x && this.y != other.y

    fun isVerticalTo(other: Point) =
        this.x == other.x

    fun isHorizontalTo(other: Point) =
        this.y == other.y

    fun determineVectorTo(other: Point): Point {
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

    fun orthogonalNeighbours(): List<Point> =
        listOf(
            //@formatter:off
                                at(0, -1),
            at(-1, 0),                      at(1, 0),
                                at(0, 1),
            //@formatter:on
        ).map { vector -> this + vector }

    fun allNeighbours(): List<Point> =
        listOf(
            //@formatter:off
            at(-1, -1),   at(0, -1),  at(1, -1),
            at(-1, 0),                      at(1, 0),
            at(-1, 1),    at(0, 1),   at(1, 1),
            //@formatter:on
        ).map { vector -> this + vector }

    override fun toString() = "($x,$y)"

    companion object {
        fun at(x: Int, y: Int): Point = Point(x, y)

        fun fromString(input: String): Point {
            val (x, y) = input.split(",").map { it.toInt() }
            return Point(x, y)
        }
    }

}