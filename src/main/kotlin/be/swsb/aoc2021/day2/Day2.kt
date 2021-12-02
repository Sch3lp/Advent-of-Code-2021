package be.swsb.aoc2021.day2

import be.swsb.aoc2021.day2.Aim.*
import kotlin.math.absoluteValue


typealias Units = UInt

data class Submarine(
    private val horizontal: HorizontalPosition = horizontalAt(0u),
    private val depth: Depth = depthAt(0u),
    private val aim: Aim = aimAt(0),
) {

    fun asSolution() = horizontal * depth

    fun accept(commands: List<String>) =
        commands.fold(this) { sub, cmdAsString -> sub.accept(cmdAsString) }

    fun accept(command: String): Submarine = when (val subCommand = command.parseAsCommand()) {
        is Command.Forwards -> forwards(subCommand.units)
        is Command.AimDown -> aimDown(subCommand.units)
        is Command.AimUp -> aimUp(subCommand.units)
    }

    private fun aimDown(units: Units): Submarine = this.copy(aim = aim + Down(units))

    private fun aimUp(units: Units): Submarine = this.copy(aim = aim + Up(units))

    private fun forwards(units: Units): Submarine {
        val newDepth = when(aim) {
            Level -> this.depth
            is Down -> dive(aim * units)
            is Up -> rise(aim * units)
        }
        return this.copy(horizontal = horizontal + units, depth = newDepth)
    }

    private fun dive(units: Units): Depth = depth + units

    private fun rise(units: Units): Depth = depth - units
}

fun aSubmarine(horizontal: UInt = 0u, depth: UInt = 0u, aim: Aim = Level) =
    Submarine(horizontalAt(horizontal), depthAt(depth), aim)

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


sealed class Aim(private val value: UInt) {
    abstract operator fun plus(down: Down): Aim
    abstract operator fun plus(up: Up): Aim

    operator fun plus(level: Level) = this
    operator fun times(units: Units) = value * units

    object Level : Aim(0u) {
        override operator fun plus(down: Down) = down
        override operator fun plus(up: Up) = up
    }
    data class Up(val _value: UInt) : Aim(_value) {
        override operator fun plus(down: Down) = aimAt(_value.toInt() - down._value.toInt())
        override operator fun plus(up: Up) = Up(this._value + up._value)
    }
    data class Down(val _value: UInt) : Aim(_value) {
        override operator fun plus(down: Down) = Down(this._value + down._value)
        override operator fun plus(up: Up) = aimAt(_value.toInt() - up._value.toInt())
    }

    override fun toString() = when(this) {
        is Level -> "Level"
        is Up -> "Up($value)"
        is Down -> "Down($value)"
    }
}

fun aimAt(value: Int) = when {
    value == 0 -> Level
    value < 0 -> Up(value.absoluteValue.toUInt())
    value > 0 -> Down(value.absoluteValue.toUInt())
    else -> throw IllegalStateException("We should never get here, I guess")
}

sealed class Command(val units: Units) {
    data class Forwards(private val _units: Units) : Command(_units)
    data class AimDown(private val _units: Units) : Command(_units)
    data class AimUp(private val _units: Units) : Command(_units)
}

fun String.parseAsCommand(): Command {
    val (command, units) = this.split(" ", limit = 2)
    return when (command) {
        "forward" -> Command.Forwards(units.toUInt())
        "down" -> Command.AimDown(units.toUInt())
        "up" -> Command.AimUp(units.toUInt())
        else -> throw IllegalArgumentException("Could not parse $this")
    }
}
