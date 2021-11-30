package be.swsb.aoc2021.common

import java.io.BufferedReader

fun String.asStream(): BufferedReader =
    {}::class.java.classLoader.getResourceAsStream(this)?.bufferedReader()
        ?: throw RuntimeException("Could not load file $this as a resource")

fun <T> String.parseWith(parser: (String) -> T): List<T> =
    this.asStream().readLines().map(parser)
