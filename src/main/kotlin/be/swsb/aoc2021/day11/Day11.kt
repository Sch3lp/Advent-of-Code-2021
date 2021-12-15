package be.swsb.aoc2021.day11

import be.swsb.aoc2021.common.Point

object Day11 {

    fun solve1(input: List<String>) : AmountOfFlashes {
        return DumboOctopusConsortium(input).step(100).amountOfFlashedOctopi
    }

    fun solve2(input: List<String>) : AmountOfFlashes {
        return 0u
    }
}

typealias AmountOfFlashes = UInt


data class DumboOctopusConsortium(
    private val octopuses: Map<Point, DumboOctopus>,
    val amountOfFlashedOctopi: UInt = 0u
) {
    constructor(input: List<String>) : this(
        input.flatMapIndexed { idx, line ->
            line.mapIndexed { lineIndex, char -> DumboOctopus(Point.at(lineIndex, idx), "$char".toUInt()) }
        }.associateBy { it.point }
    )

    constructor(input: String) : this(input.split("\n"))


    fun step(steps: Int = 1): DumboOctopusConsortium {
        return (1..steps).fold(this) { acc, _ -> acc.step() }
    }

    private fun step(): DumboOctopusConsortium {
        fun loop(affectedPoints: List<Point>, acc: Map<Point, DumboOctopus>): Map<Point, DumboOctopus> {
            if (affectedPoints.isEmpty()) return acc
            val affectedOctopuses = affectedPoints.fold(acc) { octopi, affectedPoint ->
                octopi.mapValues { (k, v) -> if (k == affectedPoint) v.increaseEnergy() else v }
            }
            val flashedOctopuses = affectedOctopuses.filterValues { it.flashedThisStep }
            val flashAffectedPoints: List<Point> =
                flashedOctopuses.keys.flatMap { it.allNeighbours() } - flashedOctopuses.keys
            return loop(
                flashAffectedPoints,
                affectedOctopuses.mapValues { (_, v) -> v.conditionallyMarkFlashedAlready() })
        }
        val steppedConsortium = loop(octopuses.keys.toList(), octopuses)

        val amountOfFlashedOctopiInStep = steppedConsortium.filterValues { it.flashedAlready }.count().toUInt()
        val resetConsortium = steppedConsortium.mapValues { (_, v) -> v.conditionallyExhaust() }
        return DumboOctopusConsortium(resetConsortium, amountOfFlashedOctopi + amountOfFlashedOctopiInStep)
    }

    fun asString(): String {
        fun octopusesToString(): String {
            if (octopuses.isEmpty()) return "No Octopus in the Consortium"
            val max = octopuses.maxOf { (k, _) -> k.x }
            return (0..max).joinToString("\n") { y ->
                (0..max).joinToString("") { x -> octopuses[Point.at(x, y)]!!.energy.toString() }.trimStart()
            }
        }
        return "Amount of Flashed Octopi: $amountOfFlashedOctopi\n" + octopusesToString()
    }

}


data class DumboOctopus(
    val point: Point,
    val energy: Energy,
    val flashedThisStep: Boolean = false,
    val flashedAlready: Boolean = false
) {
    constructor(point: Point, energy: UInt) : this(point, Energy(energy))

    fun conditionallyMarkFlashedAlready(): DumboOctopus =
        if (flashedThisStep) {
            this.copy(flashedThisStep = false, flashedAlready = true)
        } else {
            this
        }

    fun increaseEnergy(): DumboOctopus {
        val increasedEnergy = energy.increase()
        val flashedThisStep = increasedEnergy.isMaxed && !flashedAlready
        return this.copy(energy = increasedEnergy, flashedThisStep = flashedThisStep)
    }

    fun conditionallyExhaust(): DumboOctopus = DumboOctopus(point = point, energy = energy.resetWhenMaxed())
}

private const val MAX_ENERGY = 10u

data class Energy(private val value: UInt) {
    val isMaxed get() = value == MAX_ENERGY

    fun increase() =
        if (isMaxed) {
            this
        } else {
            Energy(value + 1u)
        }

    fun resetWhenMaxed() =
        if (!isMaxed) {
            this
        } else {
            Energy(0u)
        }

    override fun toString() = value.toString()
}