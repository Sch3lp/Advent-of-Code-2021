package be.swsb.aoc2021.day8

import be.swsb.aoc2021.common.readLines
import be.swsb.aoc2021.day8.Day8.deduce
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


class Day8Test {
    @Nested
    inner class SolutionTests {
        @Test
        fun `solve1 with test input returns 26`() {
            val actual = Day8.solve1("day8/testInput.txt".readLines())
            assertThat(actual).isEqualTo(26)
        }

        @Test
        fun `solve1 with actual input returns 349`() {
            val actual = Day8.solve1("day8/actualInput.txt".readLines())
            assertThat(actual).isEqualTo(349)
        }

        @Test
        fun `solve2 with test input returns 61229`() {
            val actual = Day8.solve2("day8/testInput.txt".readLines())
            assertThat(actual).isEqualTo(61229)
        }

        @Test
        fun `solve2 with actual input returns 1070957`() {
            val actual = Day8.solve2("day8/actualInput.txt".readLines())
            assertThat(actual).isEqualTo(1070957)
        }
    }

    @Nested
    inner class DecoderTests {
        @Test
        fun `Given example mapping, can decode signals to digits`() {
            val decoder = DecoderBuilder()
                .with('d' to 'a')
                .with('e' to 'b')
                .with('a' to 'c')
                .with('f' to 'd')
                .with('g' to 'e')
                .with('b' to 'f')
                .with('c' to 'g')
                .build()

            val digit: Digit = decoder.decode("dab")
            val digit2: Digit = decoder.decode("ab")

            assertThat(digit).isEqualTo(7)
            assertThat(digit2).isEqualTo(1)
        }
    }

    @Nested
    inner class DeductionTests {
        @Test
        fun `Given signals for 7 and 1, can deduce the 'a' segment`() {
            val decoderBuilder: DecoderBuilder = deduce("dab ab")

            val signal: Signal = decoderBuilder.getSignal('a')
            assertThat(signal).isEqualTo('d')
        }

        @Test
        fun `Given example signal pattern, can build the expected decoder`() {
            val decoderBuilder: DecoderBuilder = deduce("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab")

            assertThat(decoderBuilder.getSignal('a')).isEqualTo('d')
            assertThat(decoderBuilder.getSignal('b')).isEqualTo('e')
            assertThat(decoderBuilder.getSignal('c')).isEqualTo('a')
            assertThat(decoderBuilder.getSignal('d')).isEqualTo('f')
            assertThat(decoderBuilder.getSignal('e')).isEqualTo('g')
            assertThat(decoderBuilder.getSignal('f')).isEqualTo('b')
            assertThat(decoderBuilder.getSignal('g')).isEqualTo('c')
        }
    }
}
