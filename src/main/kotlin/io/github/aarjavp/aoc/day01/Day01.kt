package io.github.aarjavp.aoc.day01

import io.github.aarjavp.aoc.readFromClasspath

class Day01 {

    fun getCalibrationValuePart1(line: String): Int {
        val firstDigit = line.first { it.isDigit() }.digitToInt()
        val lastDigit = line.last { it.isDigit() }.digitToInt()
        return firstDigit * 10 + lastDigit
    }

    //TODO: use a trie
    private val digitsAsTextToValue = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )

    fun getCalibrationValuePart2(line: String): Int {
        val firstDigit = digitsAsTextToValue.mapNotNull { (asText, value) ->
            val textIndex = line.indexOf(asText).takeUnless { it == -1 }
            val digitIndex = line.indexOf(value.digitToChar()).takeUnless { it == -1 }
            return@mapNotNull listOfNotNull(textIndex, digitIndex).minOrNull()?.let { it to value }
        }.minBy { it.first }.second
        val lastDigit = digitsAsTextToValue.mapNotNull { (asText, value) ->
            val textIndex = line.lastIndexOf(asText)
            val digitIndex = line.lastIndexOf(value.digitToChar())
            return@mapNotNull listOfNotNull(textIndex, digitIndex).maxOrNull()?.let { it to value }
        }.maxBy { it.first }.second
        return firstDigit * 10 + lastDigit
    }
}

fun main() {
    val solver = Day01()

    readFromClasspath("Day01.txt").useLines { lines ->
        val sumOfCalibrations = lines.map { solver.getCalibrationValuePart1(it) }.sum()
        println("Part 1 solution: $sumOfCalibrations")
    }

    readFromClasspath("Day01.txt").useLines { lines ->
        val sumOfCalibrations = lines.map { solver.getCalibrationValuePart2(it) }.sum()
        println("Part 2 solution: $sumOfCalibrations")
    }
}
