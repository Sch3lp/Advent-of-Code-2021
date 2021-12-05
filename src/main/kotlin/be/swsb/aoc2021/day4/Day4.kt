package be.swsb.aoc2021.day4

import java.util.*

object Day4 {
    fun solve1(input: List<String>): Int {
        return solve(input).first().let { (board, winningNumber) ->
            board.findUnmarked().sum() * winningNumber
        }
    }

    fun solve2(input: List<String>): Int {
        return solve(input).last().let { (board, number) ->
            board.findUnmarked().sum() * number
        }
    }

    private fun solve(input: List<String>): List<Pair<BingoBoard, Int>> {
        val randomlyDrawnNumbers: List<Int> = input[0].split(",").map { it.toInt() }
        val bingoBoards: List<BingoBoard> = input.drop(2)
            .filterNot { it.isBlank() }
            .windowed(5, 5)
            .map { parseToBingoBoard(it) }
        val winningBoards = mutableMapOf<UUID, Pair<BingoBoard, Int>>()
        randomlyDrawnNumbers.forEach { number ->
            bingoBoards.map { bingoBoard ->
                if (bingoBoard.findAndMark(number)) {
                    winningBoards.putIfAbsent(bingoBoard.id, bingoBoard to number)
                }
            }
        }
        return winningBoards.values.toList()
    }
}

fun parseToBingoBoard(input: List<String>): BingoBoard {
    return BingoBoard(rows = input.map { rowLine ->
        Row(*rowLine.split(" ").filter { it.isNotEmpty() }.map { it.toInt() }.toIntArray())
    })
}

data class BingoBoard(val id: UUID = UUID.randomUUID(), val rows: Rows) {
    val columns: Columns
        get() = (1..rows[0].size).map { value -> Column(rows.map { it[value - 1] }) }

    private var frozen: Boolean = false

    fun findAndMark(number: Int): Boolean = whenNotFrozen(true) {
        find(number)?.mark()
        return@whenNotFrozen checkBingo().also { freezeBoardWhenBingo(it) }
    }

    fun find(number: Int): MarkableNumber? = rows.find(number)

    fun findUnmarked(): List<Int> = rows.getUnmarked()

    private fun checkBingo(): Boolean = rows.any(Row::hasBingo) || columns.any(Column::hasBingo)

    private fun freezeBoardWhenBingo(bingo: Boolean) {
        this.frozen = bingo
    }

    private fun <T> whenNotFrozen(frozenDefault: T, block: () -> T): T {
        return if (!frozen) {
            block()
        } else {
            frozenDefault
        }
    }
}

private fun Rows.find(numberToFind: Int): MarkableNumber? {
    forEach {
        val attempt = it.find { markableNumber -> markableNumber.number == numberToFind }
        if (attempt != null) return attempt
    }
    return null
}

private fun Rows.getUnmarked(): List<Int> =
    flatMap { row -> row.getUnmarked() }

data class Row(private val numbers: List<MarkableNumber>) : List<MarkableNumber> by numbers {
    constructor(vararg values: Int) : this(values.asList().map { MarkableNumber(it) })

    fun hasBingo() = numbers.all { it.marked }
    fun getUnmarked(): List<Int> {
        return numbers.filter { it.unmarked }.map { it.number }
    }
}

data class Column(private val numbers: List<MarkableNumber>) : List<MarkableNumber> by numbers {
    constructor(vararg values: Int) : this(values.asList().map { MarkableNumber(it) })

    fun hasBingo() = numbers.all { it.marked }
}
typealias Rows = List<Row>
typealias Columns = List<Column>

data class MarkableNumber(val number: Int, var marked: Boolean = false) {
    val unmarked: Boolean
        get() = !marked

    fun mark() {
        marked = true
    }
}