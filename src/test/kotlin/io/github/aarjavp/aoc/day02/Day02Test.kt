package io.github.aarjavp.aoc.day02

import io.github.aarjavp.aoc.day02.Day02.Draw
import io.github.aarjavp.aoc.day02.Day02.Game
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe

class Day02Test : FunSpec({
    val solver = Day02()

    context("Game Parsing") {
        data class TestCase(val input: String, val expectedGame: Game)

        withData(
            mapOf(
                "simple case with just 1 draw" to TestCase(
                    "Game 1: 3 blue, 4 red, 8 green",
                    Game(
                        id = 1, draws = listOf(
                            Draw(red = 4, green = 8, blue = 3),
                        )
                    )
                ),
                "simple case with 2 draws" to TestCase(
                    "Game 1: 3 blue, 4 red, 1 green; 1 red, 2 green, 6 blue",
                    Game(
                        id = 1, draws = listOf(
                            Draw(red = 4, green = 1, blue = 3),
                            Draw(red = 1, green = 2, blue = 6),
                        )
                    )
                ),
                "when not all colors are present" to TestCase(
                    "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red; 1 blue",
                    Game(
                        id = 3, draws = listOf(
                            Draw(red = 20, green = 8, blue = 6),
                            Draw(red = 4, green = 13, blue = 5),
                            Draw(red = 1, green = 5, blue = 0),
                            Draw(red = 0, green = 0, blue = 1),
                        )
                    )
                )
            )
        ) { (input, expectedGame) ->
            val actualGame = solver.parseGame(input)
            actualGame.id shouldBeExactly expectedGame.id
            actualGame.draws shouldContainExactlyInAnyOrder expectedGame.draws
        }
    }

    context("Part 1") {
        val game = Game(id = 42, draws = listOf(
            Draw(red = 4, green = 9, blue = 5),
            Draw(red = 0, green = 2, blue = 3),
            Draw(red = 10, green = 0, blue = 4),
            Draw(red = 1, green = 0, blue = 12),
            Draw(red = 0, green = 5, blue = 0),
        ))
        data class TestCase(val maxRed: Int, val maxGreen: Int, val maxBlue: Int, val expectedPossible: Boolean)

        withData(
            mapOf(
                "possible game" to TestCase(maxRed = 50, maxGreen = 50, maxBlue = 50, expectedPossible = true),
                "barely possible game" to TestCase(maxRed = 10, maxGreen = 9, maxBlue = 12, expectedPossible = true),
                "not possible game due to red" to TestCase(maxRed = 9, maxGreen = 9, maxBlue = 12, expectedPossible = false),
                "not possible game due to green" to TestCase(maxRed = 10, maxGreen = 8, maxBlue = 12, expectedPossible = false),
                "not possible game due to blue" to TestCase(maxRed = 10, maxGreen = 9, maxBlue = 11, expectedPossible = false),
            )
        ) {testCase ->
            game.hasDrawPossibleWithBagCountsOf(
                red = testCase.maxRed,
                green = testCase.maxGreen,
                blue = testCase.maxBlue
            ) shouldBe testCase.expectedPossible
        }
    }


})
