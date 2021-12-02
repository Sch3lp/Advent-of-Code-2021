package be.swsb.aoc2021.day2

import be.swsb.aoc2021.common.readLines
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day2Test {

    @Test
    fun `example input should return 150`() {
        val actual: UInt = Day2.solve1("day2/testInput.txt".readLines())

        assertThat(actual).isEqualTo(150u)
    }

    @Test
    fun `actual input should return we don't know yet`() {
        val actual: UInt = Day2.solve1("day2/actualInput.txt".readLines())

        assertThat(actual).isEqualTo(150u)
    }
}

class SubmarineTest {
    @Test
    fun `when command forwards, moves forwards X units`() {
        aSubmarine().also { sub -> assertThat(sub).isEqualTo(aSubmarine(horizontal = 0u, depth = 0u)) }
            .accept("forward 4").also { sub -> assertThat(sub).isEqualTo(aSubmarine(horizontal = 4u, depth = 0u)) }
            .accept("forward 3").also { sub -> assertThat(sub).isEqualTo(aSubmarine(horizontal = 7u, depth = 0u)) }
    }

    @Test
    fun `when command down, lowers the submarine X units`() {
        aSubmarine().also { sub -> assertThat(sub).isEqualTo(aSubmarine(horizontal = 0u, depth = 0u)) }
            .accept("down 7").also { sub -> assertThat(sub).isEqualTo(aSubmarine(horizontal = 0u, depth = 7u)) }
            .accept("down 8").also { sub -> assertThat(sub).isEqualTo(aSubmarine(horizontal = 0u, depth = 15u)) }
    }

    @Test
    fun `when command up, raises the submarine X units`() {
        aSubmarine(horizontal = 0u, depth = 15u)
            .accept("up 4").also { sub -> assertThat(sub).isEqualTo(aSubmarine(horizontal = 0u, depth = 11u)) }
            .accept("up 3").also { sub -> assertThat(sub).isEqualTo(aSubmarine(horizontal = 0u, depth = 8u)) }
    }

    @Test
    fun `when command up, and submarine is already at 0, doesn't turn into an aerosub`() {
        aSubmarine().also { sub -> assertThat(sub).isEqualTo(aSubmarine(horizontal = 0u, depth = 0u)) }
            .accept("up 1").also { sub -> assertThat(sub).isEqualTo(aSubmarine(horizontal = 0u, depth = 0u)) }
    }

    @Test
    fun `when accepting multiple commands, the sub executes them in order`() {
        aSubmarine()
            .accept(listOf("down 5", "forward 2", "up 1"))
            .also { assertThat(it).isEqualTo(aSubmarine(horizontal = 2u, depth = 4u)) }
    }
}

typealias Units = UInt

data class Submarine(
    private val horizontal: HorizontalPosition = horizontalAt(0u),
    private val depth: Depth = depthAt(0u)
) {

    fun asSolution() = horizontal * depth

    fun accept(commands: List<String>) =
        commands.fold(this) { sub, cmdAsString -> sub.accept(cmdAsString) }

    fun accept(command: String): Submarine {
        return when(val subCommand = command.parseAsCommand()) {
            is Command.Forwards -> forwards(subCommand.units)
            is Command.Dive -> dive(subCommand.units)
            is Command.Rise -> rise(subCommand.units)
        }
    }

    private fun forwards(units: Units): Submarine {
        return this.copy(horizontal = horizontal + units)
    }

    private fun dive(units: Units): Submarine {
        return this.copy(depth = depth + units)
    }

    private fun rise(units: Units): Submarine {
        return this.copy(depth = depth - units)
    }
}

fun aSubmarine(horizontal: UInt = 0u, depth: UInt = 0u) =
    Submarine(horizontalAt(horizontal), depthAt(depth))

@JvmInline
value class Depth(private val _value: UInt) {
    operator fun plus(units: Units) = Depth(_value + units)
    operator fun minus(units: Units) = Depth((_value.toInt() - units.toInt()).coerceAtLeast(0).toUInt())
    operator fun times(_value: UInt) = _value * this._value
    operator fun times(horizontalPosition: HorizontalPosition) = horizontalPosition * this._value
}

fun depthAt(value: UInt) = Depth(value)

@JvmInline
value class HorizontalPosition(private val _value: UInt) {
    operator fun plus(units: Units) = HorizontalPosition(_value + units)
    operator fun times(depth: Depth) = depth * this._value
    operator fun times(_value: UInt) = _value * this._value
}

fun horizontalAt(value: UInt) = HorizontalPosition(value)

sealed class Command(val units: Units) {
    data class Forwards(private val _units: Units): Command(_units)
    data class Dive(private val _units: Units): Command(_units)
    data class Rise(private val _units: Units): Command(_units)
}

fun String.parseAsCommand(): Command {
    val (command, units) = this.split(" ", limit = 2)
    return when(command) {
        "forward" -> Command.Forwards(units.toUInt())
        "down" -> Command.Dive(units.toUInt())
        "up" -> Command.Rise(units.toUInt())
        else -> throw IllegalArgumentException("Could not parse $this")
    }
}

object Day2 {
    fun solve1(commandsAsStrings: List<String>): UInt {
        return aSubmarine().accept(commandsAsStrings).asSolution()
    }
}