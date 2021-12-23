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

//    Java Heap space T_T
    fun solve2(input: List<String>): Long {
        val (initialTemplate, rules) = parse(input)
        val initialCombinations: List<Combination> =
            initialTemplate.windowed(2) { elements -> elements.first() with elements.last() }
        val initialElementCount: Map<Element, Long> = initialTemplate.groupBy { it }.mapValues { (_,v) -> v.size.toLong() }
        val polymer = (1..40).fold(Polymer(initialCombinations, initialElementCount)) { polymer, _ ->
            polymer.expand(rules)
        }
        val mostCommon = polymer.mostCommon
        val leastCommon = polymer.leastCommon
        return mostCommon - leastCommon
    }

//    Also Java Heap Space
//    fun solve2(input: List<String>): Long {
//        val (initialTemplate, rules) = parse(input)
//        var combinations: MutableList<Combination> =
//            initialTemplate.windowed(2) { elements -> elements.first() with elements.last() }.toMutableList()
//        val countPerElement: MutableMap<Element, Long> = initialTemplate.groupBy { it }.mapValues { (_,v) -> v.size.toLong() }.toMutableMap()
//        val snarf: LinkedList<Element> = LinkedList(combinations.flatMap { (left, right) -> listOf(left,right) })
//        repeat(40) {
//            val tmpCombinations = combinations.toList()
//            combinations.clear()
//            tmpCombinations.forEach { combination ->
//                val insertion =
//                    rules.find { rule -> rule.combination == combination }?.insertion?.also { insertedElement ->
//                        countPerElement[insertedElement] = countPerElement[insertedElement]?.plus(1) ?: 1
//                    }
//                combinations += combination.insert(insertion)
//            }
//        }
//        val mostCommon = countPerElement.values.maxOf { it }
//        val leastCommon = countPerElement.values.minOf { it }
//        return mostCommon - leastCommon
//    }

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
    private val combinations: List<Combination>,
    private val countPerElement: Map<Element, Long>
) {
    val mostCommon: Long by lazy { countPerElement.values.maxOf { it } }
    val leastCommon: Long by lazy { countPerElement.values.minOf { it } }

    fun expand(rules: List<Rule>): Polymer {
        val mutablePolymer = countPerElement.toMutableMap()
        val lastCreatedCombinations = combinations.asSequence().flatMap { combination ->
            val insertion = rules.find { rule -> rule.combination == combination }?.insertion?.also { insertedElement ->
                mutablePolymer[insertedElement] = mutablePolymer[insertedElement]?.plus(1) ?: 1
            }
            combination.insert(insertion)
        }
        return Polymer(lastCreatedCombinations.toList(), mutablePolymer)
    }
}