package be.swsb.aoc2021.common

import org.assertj.core.api.SoftAssertions

fun softly(assertionsBlock:SoftAssertions.() -> Unit) {
    val softAssertions = SoftAssertions()
    softAssertions.assertionsBlock()
    softAssertions.assertAll()
}