package be.fgov.sfpd.aoc.aoc2021.common

import java.io.BufferedReader

internal fun String.asFile(): BufferedReader {
    // we need classLoader so we can put files in the resources root dir and find them
    val resource = {}::class.java.classLoader.getResourceAsStream(this)
    return resource?.bufferedReader() 
        ?: throw IllegalStateException("Something went wrong reading from file $this")
}