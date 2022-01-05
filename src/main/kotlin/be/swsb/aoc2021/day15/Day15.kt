package be.swsb.aoc2021.day15

import be.swsb.aoc2021.common.Point
import be.swsb.aoc2021.common.Point.Companion.at

object Day15 {
    fun solve1(input: List<String>) : Int {
        val caveWalls = input.flatMapIndexed { idx, line ->
            line.mapIndexed { lineIndex, char -> CaveWall(Point.at(lineIndex, idx), "$char".toInt()) }
        }
        val scan = Scan(caveWalls)
        return scan.findLeastRiskyPath().also { println("Least Risky Path: $it") }.summedRiskLevel
    }
    fun solve2(input: List<String>) : Int = 0
}

typealias RiskLevel = Int
data class CaveWall(val point: Point, val riskLevel: RiskLevel)

typealias PathBuilder = MutableList<CaveWall>
typealias Path = List<CaveWall>
val Path.summedRiskLevel: Int
    get() = this.sumOf { wall -> wall.riskLevel }

data class Scan(val walls: List<CaveWall>) {
    private val locations by lazy { walls.associateBy { it.point } }
    private val adjacencyList: Map<CaveWall, List<CaveWall>> by lazy{ walls.associateWith { neighboursOf(it.point) } }

    fun wallsAt(points: List<Point>): List<CaveWall> =
        points.mapNotNull { point -> locations[point] }

    fun neighboursOf(point: Point) : List<CaveWall> = wallsAt(point.orthogonalNeighbours())

    fun findLeastRiskyPath(): Path {
        val start = walls.first().also { println("start: $it") }
        val end = walls.last().also { println("end: $it") }
        val visited = walls.associateWith { false }.toMutableMap()
        return depthFirstSearch(start, end, visited, path = mutableListOf(start), leastRiskyPath = mutableListOf(
            CaveWall(at(99,99), Int.MAX_VALUE)
        ))
    }

    private fun depthFirstSearch(
        from: CaveWall,
        to: CaveWall,
        visited: MutableMap<CaveWall, Boolean>,
        path: PathBuilder = mutableListOf(),
        leastRiskyPath: Path
    ): Path {
        fun visit(cavewall: CaveWall) { visited[cavewall] = true }
        fun backtrack(cavewall: CaveWall) { visited[cavewall] = false }
        if (from == to) {
            return if (path.summedRiskLevel < leastRiskyPath.summedRiskLevel) path else leastRiskyPath
        }
        var currentLeastRiskyPath = leastRiskyPath.toList()
        visit(from)
        neighboursOf(from.point).filter { it.riskLevel <= from.riskLevel }.forEach { caveWall ->
            if (!visited.getValue(caveWall)) {
                path += caveWall
                currentLeastRiskyPath = depthFirstSearch(caveWall, to, visited, path, leastRiskyPath).toList()
                path.removeLast()
            }
        }
        backtrack(from)
        return currentLeastRiskyPath
    }
}