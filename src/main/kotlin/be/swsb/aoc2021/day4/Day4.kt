package be.swsb.aoc2021.day4

object Day4 {
    fun solve1(input: List<String>): Int {
        val randomlyDrawnNumbers : List<Int> = input[0].split(",").map { it.toInt() }
        val bingoBoards: List<BingoBoard> = input.drop(2)
            .filterNot { it.isBlank() }
            .windowed(5,5)
            .map { parseToBingoBoard(it) }
        randomlyDrawnNumbers.forEach { number ->
            bingoBoards.map { bingoBoard ->
                if (bingoBoard.findAndMark(number)) {
                    val sumOfUnmarked = bingoBoard.findUnmarked().sum()
                    return sumOfUnmarked * number
                }
            }
        }
        throw IllegalStateException("There were no randomlyDrawnNumber of bingo boards?")
    }
}

fun parseToBingoBoard(input: List<String>): BingoBoard {
    return BingoBoard(input.map { rowLine ->
        Row(*rowLine.split(" ").filter { it.isNotEmpty() }.map { it.toInt() }.toIntArray())
    })
}

data class BingoBoard(val rows: Rows) {
    val columns: Columns
        get() = (1..rows[0].size).map { value -> Column(rows.map { it[value - 1] }) }

    fun findAndMark(number: Int): Bingo {
        find(number)?.mark()
        return checkBingo()
    }

    fun find(number: Int): MarkableNumber? {
        return rows.find(number)
    }

    fun findUnmarked(): List<Int> {
        return rows.getUnmarked()
    }

    private fun checkBingo(): Bingo {
        return rows.any(Row::hasBingo) || columns.any(Column::hasBingo)
    }
}

typealias Bingo = Boolean

private fun Rows.find(numberToFind: Int): MarkableNumber? {
    forEach {
        val attempt = it.find { markableNumber -> markableNumber.number == numberToFind }
        if (attempt != null) return attempt
    }
    return null
}

private fun Rows.getUnmarked(): List<Int> {
    return flatMap { row -> row.getUnmarked() }
}

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