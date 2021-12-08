package be.swsb.aoc2021.day7

import kotlin.math.*

object Day7 {
    fun solve1(input: List<Int>): Fuel {
        return findMostOptimalCost(input)
    }

    fun solve2(input: List<Int>): Fuel {
        return findMostOptimalCost2(input)
    }

}


fun findMostOptimalCost(positions: List<Position>): Fuel {
    val crabInTheMiddle: Position = positions.sorted()[positions.size / 2]
    return positions.fold(0) { acc, cur -> acc + cur.diff(crabInTheMiddle) }
}


fun calculateFuelSpent(pos1: Position, pos2: Position): Fuel {
    val steps = pos1.diff(pos2)
    fun fuelExpenditureFor(steps: Int): Fuel {
        return steps * (steps + 1) / 2
    }
    return fuelExpenditureFor(steps)
}

fun findMostOptimalCost2(positions: List<Int>): Fuel {
    val average = positions.average()
    val probablyBestPosRoundedDown = floor(average).toInt()
    val probablyBestPosRoundedUp = ceil(average).toInt()
    val optimalFuelCostWithRoundingDown =
        positions.fold(0) { acc, cur -> acc + calculateFuelSpent(cur, probablyBestPosRoundedDown) }
    val optimalFuelCostWithRoundingUp =
        positions.fold(0) { acc, cur -> acc + calculateFuelSpent(cur, probablyBestPosRoundedUp) }

    return min(optimalFuelCostWithRoundingDown, optimalFuelCostWithRoundingUp)
}

fun Position.diff(other: Position): Fuel = abs(this - other)

typealias Position = Int
typealias Fuel = Int