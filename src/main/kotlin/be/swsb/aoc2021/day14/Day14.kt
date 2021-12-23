package be.swsb.aoc2021.day14

object Day14 {
    fun solve1(input: List<String>): Int {
        val (initialTemplate, rules) = parse(input)
        val polymer = (1..10).fold(initialTemplate) { template, _ ->
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

    fun solve2(input: List<String>): Long {
        val (initialTemplate, rules) = parse(input)
        val initialElementCount: Map<Element, Long> = initialTemplate.groupBy { it }.mapValues { (_,v) -> v.size.toLong() }
        val initialCombinations: List<Combination> = initialTemplate.zipWithNext { first, second -> first with second }
        val initialCombinationOccurrences : Map<Combination, Long> = initialCombinations.groupBy { it }.mapValues { (_,v) -> v.size.toLong() }
        val initialPolymer = Polymer(initialCombinationOccurrences, initialElementCount)
        val polymer = (1..40).fold(initialPolymer) { polymer, _ -> polymer.expand(rules) }
        val mostCommon = polymer.mostCommon
        val leastCommon = polymer.leastCommon
        return mostCommon - leastCommon
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

data class Combination(val left: Element, val right: Element) {
    fun inject(insertion: Element?) = insertion?.let { "$left$it$right" } ?: "$left$right"
    fun insert(insertion: Element?) =
        insertion?.let { listOf(left with insertion, insertion with right) } ?: emptyList()

    override fun toString() = "$left$right"
}

typealias Element = Char

infix fun Element.with(other: Element): Combination = Combination(this, other)

data class Polymer(
    private val combinationOccurrences: Map<Combination, Long>,
    private val countPerElement: Map<Element, Long>
) {
    val mostCommon: Long by lazy { countPerElement.values.maxOf { it } }
    val leastCommon: Long by lazy { countPerElement.values.minOf { it } }

    fun expand(rules: List<Rule>): Polymer {
        val newCombinationOccurrences : MutableMap<Combination, Long> = mutableMapOf()
        val mutablePolymer = countPerElement.toMutableMap()
        combinationOccurrences.entries.forEach { (combination, occ) ->
            val insertion = rules.find { rule -> rule.combination == combination }?.insertion?.also { insertedElement ->
                mutablePolymer[insertedElement] = mutablePolymer[insertedElement]?.plus(occ) ?: occ
            }
            combination.insert(insertion).forEach { newCombo ->
                newCombinationOccurrences[newCombo] = newCombinationOccurrences[newCombo]?.plus(occ) ?: occ
            }
        }
        return Polymer(newCombinationOccurrences, mutablePolymer)
    }
}