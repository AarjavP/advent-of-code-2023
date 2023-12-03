package io.github.aarjavp.aoc.day01

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.ints.shouldBeExactly

internal class Day01Test: FunSpec({
    val solver = Day01()

    data class TestCase(val input: String, val expectedValue: Int)

    context("Part 1") {
        withData(
            TestCase(input = "123", expectedValue = 13),
            TestCase(input = "a321b", expectedValue = 31),
            TestCase(input = "1a1", expectedValue = 11),
            TestCase(input = "4", expectedValue = 44),
            TestCase(input = "one445", expectedValue = 45),
        ) { (input, expectedValue) ->
            solver.getCalibrationValuePart1(input) shouldBeExactly expectedValue
        }
    }

    context("Part 2") {
        val testCases = listOf(
            TestCase(input = "721sixsdzbqtskbpkqbcmgmlpk5psrhr", expectedValue = 75),
            TestCase(input = "pqxdxcx5", expectedValue = 55),
            TestCase(input = "twozsf2five", expectedValue = 25),
            TestCase(input = "eightwofourlqrxtwone", expectedValue = 81),
            TestCase(input = "twozsf2x", expectedValue = 22),
            TestCase(input = "twozsf8s", expectedValue = 28),
            TestCase(input = "zzsf8sixtynine", expectedValue = 89),
        )

        context("Standard search approach") {
            withData(testCases) { (input, expectedValue) ->
                solver.getCalibrationValuePart2(input) shouldBeExactly expectedValue
            }
        }

        context("Trie approach") {
            withData(testCases) { (input, expectedValue) ->
                solver.getCalibrationValuePart2Trie(input) shouldBeExactly expectedValue
            }
        }
    }
})
