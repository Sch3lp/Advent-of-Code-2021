package be.swsb.aoc2021.day9

import be.swsb.aoc2021.common.Point
import java.util.*

object Day9 {
    fun solve1(input: List<String>): Int {
        val heightmap = input.toHeightmap()
        return heightmap.findLowest().sumOf { it.riskLevel }
    }

    fun solve2(input: List<String>): Int {
        val heightmap = input.toHeightmap()
        val allBasins = heightmap.findBasins()
        checkNoMergingBasins(allBasins)
        val threeLargestBasins = allBasins
            .sortedByDescending { it.size }
            .take(3)
        return threeLargestBasins.map { it.size }.reduce { prev, cur -> prev * cur }
    }

    private fun checkNoMergingBasins(allBasins: List<Basin>) {
        val mergingBasins = allBasins.map { basin ->
            (allBasins - basin).filter { otherBasin -> basin.containsAnyOf(otherBasin) }
        }.filterNot { it.isEmpty() }
        if (mergingBasins.isNotEmpty()) {
            throw IllegalStateException(mergingBasins.joinToString("\n") { "These basins are merging: $it" })
        }
    }
}


fun List<String>.toHeightmap() = HeightMap(
    this.flatMapIndexed { idx, line ->
        line.mapIndexed { lineIndex, char -> CaveTile(Point.at(lineIndex, idx), "$char".toInt()) }
    }
)

data class HeightMap(val tiles: List<CaveTile>) {

    private val _no9s
        get() = tiles.filterNot { it.height == 9 }

    private val locations by lazy { _no9s.associateBy { it.point } }

    fun tilesAt(points: List<Point>): List<CaveTile> =
        tilesAtUsingMap(points)

    // 38.618 without lazy locations
    //  0.328 with lazy locations
    fun tilesAtUsingMap(points: List<Point>): List<CaveTile> =
        points.mapNotNull { point -> locations[point] }

    // 14.221 without lazy locations
    // 3.594 with lazy locations
    fun tilesAtUsingList(points: List<Point>): List<CaveTile> =
        locations.filter { (point,_) -> point in points }.values.toList()

    fun findLowest() =
        _no9s.filter { it.isLowest(this) }

    fun findBasins(): List<Basin> {
        val basinBottoms = findLowest()
        return basinBottoms.map { basinBottom ->
            val basin = accumulateBasin(setOf(basinBottom))
            Basin(tiles = basin)
        }
    }

    private fun accumulateBasin(basin: Set<CaveTile>): Set<CaveTile> {
        val possibleNextBasinPoints = basin.flatMap { basinPoint ->
            basinPoint.neighbors(this).filter { it.height > basinPoint.height }
        } - basin
        return if (possibleNextBasinPoints.isEmpty()) {
            basin
        } else {
            accumulateBasin(basin + possibleNextBasinPoints)
        }
    }
}

data class CaveTile(val point: Point, val height: Height) {
    val riskLevel
        get() = 1 + height

    fun isLowest(heightMap: HeightMap): Boolean =
        neighbors(heightMap).all { neighbour -> neighbour.height > height }

    fun neighbors(heightMap: HeightMap): List<CaveTile> {
        return heightMap.tilesAt(this.point.orthogonalNeighbours())
    }

    override fun toString() = "$point:$height"
}

class Basin(private val tiles: Set<CaveTile>, val id: UUID = UUID.randomUUID()) {
    val size: Int
        get() = tiles.size

    val lowPoint: Point
        get() = tiles.minByOrNull { it.height }!!.point

    fun containsAnyOf(other: Basin): Boolean {
        if (this == other) return true
        if (this.tiles.isEmpty() || other.tiles.isEmpty()) return false
        return this.tiles.any { tile -> tile in other.tiles }
    }

    override fun toString() = "Basin at $lowPoint of size $size with " + tiles.joinToString { it.toString() }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Basin) return false
        return this.id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}

fun heightAt(x: Int, y: Int, z: Int) = CaveTile(Point.at(x, y), z)
typealias Height = Int