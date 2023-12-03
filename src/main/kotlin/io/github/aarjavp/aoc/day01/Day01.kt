package io.github.aarjavp.aoc.day01

import io.github.aarjavp.aoc.readFromClasspath
import java.util.LinkedList
import kotlin.time.measureTime

class Day01 {

    fun getCalibrationValuePart1(line: String): Int {
        val firstDigit = line.first { it.isDigit() }.digitToInt()
        val lastDigit = line.last { it.isDigit() }.digitToInt()
        return firstDigit * 10 + lastDigit
    }

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

    class MyTrie {
        class TrieNode {
            val children: MutableMap<Char, TrieNode> = mutableMapOf()
            var value: Int? = null
        }
        val root: TrieNode = TrieNode()

        fun register(text: CharSequence, value: Int) {
            require(text.isNotBlank())
            require(value in 1..9)
            var currentNode = root
            for (char in text) {
                currentNode = currentNode.children.computeIfAbsent(char) { TrieNode() }
            }
            require(currentNode.value == null || value == currentNode.value)
            currentNode.value = value
        }

        fun findFirst(text: String): Int? {
            for (i in text.indices) {
                var currentNode = root
                for (char in text.subSequence(i, text.length)) {
                    currentNode = currentNode.children[char] ?: break
                    if (currentNode.value != null) return currentNode.value
                }
            }
            return null
        }
    }

    val firstDigitSearchTrie: MyTrie = MyTrie().apply {
        digitsAsTextToValue.forEach { register(it.key, it.value) }
        for (i in 1..9) {
            register(i.toString(), i)
        }
    }
    val lastDigitSearchTrie: MyTrie = MyTrie().apply {
        digitsAsTextToValue.forEach { register(it.key.reversed(), it.value) }
        for (i in 1..9) {
            register(i.toString(), i)
        }
    }

    fun getCalibrationValuePart2Trie(line: String): Int {
        val firstDigit = firstDigitSearchTrie.findFirst(line) ?: error("no digit found! searched: $line")
        val lastDigit = lastDigitSearchTrie.findFirst(line.reversed()) ?: error("no digit found! searched: ${line.reversed()}")
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
        val sumOfCalibrations: Int
        val duration = measureTime {
            sumOfCalibrations = lines.map { solver.getCalibrationValuePart2(it) }.sum()
        }
        println("Part 2 solution: $sumOfCalibrations in $duration")
    }

    readFromClasspath("Day01.txt").useLines { lines ->
        val sumOfCalibrations: Int
        val duration = measureTime {
            sumOfCalibrations = lines.map { solver.getCalibrationValuePart2Trie(it) }.sum()
        }
        println("Part 2 solution with trie: $sumOfCalibrations in $duration")
    }

}
