package be.swsb.aoc2021.day1

object Day1 {
    fun solve1(depthMeasurementInput: List<String>): Int {
        return depthMeasurementInput
            .map { it.toDepthMeasurement() }
            .zipWithNext()
            .count { (prev, cur) -> prev < cur }
    }
    fun solve2(depthMeasurementInput: List<String>): Int {
        return depthMeasurementInput
            .map { it.toDepthMeasurement() }
            .toDepthMeasurementSums()
            .zipWithNext()
            .count { (prev, cur) -> prev < cur }
    }
}

private fun String.toDepthMeasurement() = DepthMeasurement(this)

@JvmInline
private value class DepthMeasurement(val value: Int) {
    constructor(value: String) : this(value.toInt())

    operator fun compareTo(other: DepthMeasurement) = value.compareTo(other.value)
}

private fun List<DepthMeasurement>.toDepthMeasurementSums(): List<DepthMeasurementSum> {
    return this
        .windowed(3)
        .map { it.let { (m1, m2, m3) -> DepthMeasurementSum(m1, m2, m3) } }
}

private data class DepthMeasurementSum(
    private val measurement1: DepthMeasurement,
    private val measurement2: DepthMeasurement,
    private val measurement3: DepthMeasurement,
) {
    operator fun compareTo(other: DepthMeasurementSum): Int {
        return this.sum().compareTo(other.sum())
    }

    private fun sum() = listOf(measurement1, measurement2, measurement3).sumOf { it.value }
}
