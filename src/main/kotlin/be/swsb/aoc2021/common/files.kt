package be.swsb.aoc2021.common

fun String.readLines() =
    {}::class.java.classLoader.getResourceAsStream(this)?.bufferedReader()?.readLines()
        ?: throw RuntimeException("Could not load file $this as a resource")

fun <T> String.parseWith(parser: (String) -> T): List<T> =
    this.readLines().map(parser)
