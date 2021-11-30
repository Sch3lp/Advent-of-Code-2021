package be.swsb.aoc2021.common

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FilesTest {

    @Test
    fun `can read files as list of strings`() {
        val actual : List<String> = "input.txt".asStream().readLines()

        assertThat(actual).containsExactly(
            "1,Bruce,Wayne",
            "2,Edward,Nigma",
        )
    }

    @Test
    fun `can map list of strings using a parser`() {
        val actual = "input.txt".parseWith(Person::fromString)

        assertThat(actual).containsExactly(
            Person(Id(1u), "Bruce", "Wayne"),
            Person(Id(2u), "Edward", "Nigma"),
        )
    }
}


private data class Person(val id: Id, val firstname: String, val lastname: String) {
    companion object {
        fun fromString(value: String) : Person {
            val (id,firstname,lastname) = value.split(",")
            return Person(Id(id),firstname,lastname)
        }
    }
}
@JvmInline value class Id(private val _value: UInt) {
    constructor(value: String): this(value.toUInt())
}