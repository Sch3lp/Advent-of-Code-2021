package be.swsb.aoc2021.day14

object Day14 {
    fun solve1(input: List<String>): Int {
        val (initialTemplate, rules) = parse(input)
        val polymer = (1..10).fold(initialTemplate) { template, iteration ->
            expandPolymer(template, rules)
        }
        val mostCommon = polymer.maxOf { c -> polymer.count { it == c } }
        val leastCommon = polymer.minOf { c -> polymer.count { it == c } }
        return mostCommon - leastCommon
    }

    fun expandPolymer(template: String, rules: List<Rule>): String =
        template.windowed(2) { combination -> combination.first() with combination.last() }
            .joinToString("", prefix = template.take(1)) { combination ->
                val insertion = rules.find { rule -> rule.combination == combination }?.insertion
                combination.inject(insertion).drop(1)
            }

    fun solve2(input: List<String>): Int {
        return 0
    }

    fun parse(input: List<String>): Pair<String, List<Rule>> {
        val rules = input.subList(input.indexOf("") + 1, input.size)
        val template = input.subList(0, input.indexOf("")).first()
        return template to rules.map { it.parseToRule() }
    }
}

data class Rule(val combination: Combination, val insertion: Char)

private fun String.parseToRule(): Rule {
    val (combo, insert) = this.split(" -> ")
    val (one, two) = combo.toCharArray()
    return Rule(one with two, insert.toCharArray()[0])
}

data class Combination(val left: Char, val right: Char) {
    fun inject(insertion: Char?) = insertion?.let { "$left$it$right" } ?: "$left$right"
    override fun toString() = "$left$right"
}

infix fun Char.with(other: Char): Combination = Combination(this, other)