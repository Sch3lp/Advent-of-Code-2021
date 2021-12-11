package be.swsb.aoc2021.day9

import be.swsb.aoc2021.common.Point

object Day9 {
    fun solve1(input: List<String>): Int {
        val heightmap = input.toHeightmap()
        return heightmap.tiles.filter { it.isLowest(heightmap) }.sumOf { it.riskLevel }
    }
    fun solve2(input: List<String>): Int {
        return 0
    }
}


fun List<String>.toHeightmap() = HeightMap(
    this.flatMapIndexed { idx, line ->
        line.mapIndexed{ lineIndex, char -> CaveTile(Point.at(lineIndex, idx), "$char".toInt())}
    }
)

data class HeightMap(val tiles: List<CaveTile>) {
    private val locations
        get() = tiles.associateBy { it.point }

    fun tilesAt(points: List<Point>): List<CaveTile> =
        locations.filter { (point,tile) -> point in points }.values.toList()
}

data class CaveTile(val point: Point, val height: Height) {

    val riskLevel
        get() = 1 + height

    fun isLowest(heightMap: HeightMap): Boolean =
        neighbors(heightMap).all { neighbour -> neighbour.height > height }

    private fun neighbors(heightMap: HeightMap): List<CaveTile> {
        return heightMap.tilesAt(this.point.orthogonalNeighbours())
    }

    override fun toString() = "$point:$height"
}

fun heightAt(x: Int, y: Int, z: Int) = CaveTile(Point.at(x, y), z)
typealias Height = Int