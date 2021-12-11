package be.swsb.aoc2021.day8


private const val amountOfSignalsToDisplay1 = 2
private const val amountOfSignalsToDisplay4 = 4
private const val amountOfSignalsToDisplay7 = 3
private const val amountOfSignalsToDisplay8 = 7

object Day8 {

    fun solve1(input: List<String>): Int {
        return input.sumOf { line ->
            val digits: List<String> = line.substringAfter("| ").split(" ")
            digits.count {
                it.length in listOf(
                    amountOfSignalsToDisplay1,
                    amountOfSignalsToDisplay4,
                    amountOfSignalsToDisplay7,
                    amountOfSignalsToDisplay8
                )
            }
        }
    }

    fun solve2(input: List<String>): Int {
        return input.sumOf { line ->
            val (signalPattern, displaysAsString) = line.split("| ")
            val displays = displaysAsString.split(" ")
            val decoder = deduce(signalPattern).build()
            displays.joinToString("") { encodedDisplay -> decoder.decode(encodedDisplay) }.toInt()
        }
    }

    fun deduce(signalPattern: String): DecoderBuilder {
        val encodedDigits = signalPattern.split(" ")
        val encodedOne = encodedDigits.first { it.count() == 2 }
        val encodedSeven = encodedDigits.first { it.count() == 3 }
        val encodedFour = encodedDigits.first { it.count() == 4 }

        return DecoderBuilder()
            .deduceSegmentA(encodedOne, encodedSeven)
            .deduceOtherSegments(encodedDigits, encodedFour)
    }


}

class DecoderBuilder(private val map: Map<Signal, Segment> = emptyMap()) {
    fun with(mapping: Pair<Signal, Segment>): DecoderBuilder {
        return DecoderBuilder(map + listOf(mapping).toMap())
    }

    fun deduceSegmentA(
        encodedOne: String,
        encodedSeven: String
    ): DecoderBuilder {
        val signalForSegmentA = encodedSeven.diff(encodedOne)
        return with(signalForSegmentA[0] to 'a')
    }

    /*
    If we add (overlay) all encoded digits on top of each other, and then add (overlay) the encoded 4 exactly 2 more times we get unique amounts for all segments
    The top segment we already know.
     0 + 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 4 + 4 - segmentA
      ----
     8    10
     8    10
      9999
     4    11
     4    11
      7777
     */
    fun deduceOtherSegments(encodedDigits: List<String>, encodedFour: String): DecoderBuilder {
        val occurrencesPerSignal = (encodedDigits + encodedFour + encodedFour)
            .joinToString("")
            .replace(getSignal('a').toString(), "") //drop the signal for the segment we already know before grouping
            .groupBy { it }
            .map { (k, v) -> v.count() to k }
            .toMap()
        return with(occurrencesPerSignal[8]!! to 'b')
            .with(occurrencesPerSignal[10]!! to 'c')
            .with(occurrencesPerSignal[9]!! to 'd')
            .with(occurrencesPerSignal[4]!! to 'e')
            .with(occurrencesPerSignal[11]!! to 'f')
            .with(occurrencesPerSignal[7]!! to 'g')
    }

    fun build(): Decoder {
        return Decoder(map)
    }

    fun getSignal(segment: Segment): Signal {
        return map.filterValues { it == segment }.keys.first()
    }

    private fun String.diff(other: String) = this.filter { it !in other.toList() }
}


class Decoder(private val map: Map<Signal, Segment>) {

    init {
        check(map.keys.containsAll(('a'..'g').toList())) { "Tried building a Decoder with incomplete or incorrect signals: ${map.keys}" }
        check(map.values.containsAll(('a'..'g').toList())) { "Tried building a Decoder with incomplete or incorrect segments: ${map.values}" }
    }

    private val digits: Map<String, String> = listOf(
        "abcefg" to "0",
        "cf" to "1",
        "acdeg" to "2",
        "acdfg" to "3",
        "bcdf" to "4",
        "abdfg" to "5",
        "abdefg" to "6",
        "acf" to "7",
        "abcdefg" to "8",
        "abcdfg" to "9",
    ).toMap()

    fun decode(encodedDisplay: CharSequence): Digit {
        val decodedDisplay = encodedDisplay.map { map[it] }.sortedBy { it }.joinToString("")
        return digits[decodedDisplay]!!
    }
}
typealias Digit = String
typealias Signal = Char
typealias Segment = Char
