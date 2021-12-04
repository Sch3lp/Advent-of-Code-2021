package be.swsb.aoc2021.day3

object Day3 {
    fun getPowerConsumption(input: List<String>): Pair<String, String> {
        var result = (1..input[0].length).map { 0 }
        input.forEach { bitNumber ->
            val bitAsCalculcations = bitNumber.map { if (it == '1') 1 else -1 }
            result = bitAsCalculcations.zip(result) { a, b -> a + b }
        }
        val gamma = result.map { if (it >= 0) 1 else 0 }
        val epsilon = gamma.map { if (it == 0) 1 else 0 }

        return gamma.joinToString("") to epsilon.joinToString("")
    }

    fun getLifeSupportRating(input: List<String>): Pair<Int, Int> {
        return getOxygenGeneratorRating(input) to getCO2ScrubberRating(input)
    }

    private fun getOxygenGeneratorRating(input: List<String>): Int {
        return getRatingByCriteria(input) { tmp -> getPowerConsumption(tmp).first }
    }

    private fun getCO2ScrubberRating(input: List<String>): Int {
        return getRatingByCriteria(input) { tmp -> getPowerConsumption(tmp).second }
    }

    private fun getRatingByCriteria(input: List<String>, criteriaRetriever: (List<String>) -> String): Int {
        var tmp: MutableList<String> = input.toMutableList()
        var idx = 0
        while (tmp.size > 1) {
            val criteria = criteriaRetriever(tmp)
            tmp = tmp.filter { bit -> bit[idx] == criteria[idx] }.toMutableList()
            idx++
        }
        return tmp[0].toDecimal()
    }

}

fun String.toDecimal() = toInt(2)
