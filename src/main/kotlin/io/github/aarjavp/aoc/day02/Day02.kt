package io.github.aarjavp.aoc.day02

import io.github.aarjavp.aoc.day02.Day02.Draw
import io.github.aarjavp.aoc.readFromClasspath

class Day02 {
    enum class Color(val text: String) {
        RED("red"), GREEN("green"), BLUE("blue")
    }
    data class Draw(val red: Int = 0, val green: Int = 0, val blue: Int = 0)

    data class Game(val id: Int, val draws: List<Draw>) {
        fun hasDrawPossibleWithBagCountsOf(red: Int, green: Int, blue: Int): Boolean {
            return draws.all { actualDraw ->
                actualDraw.red <= red && actualDraw.blue <= blue && actualDraw.green <= green
            }
        }
    }

    fun parseGame(line: String): Game {
        val id = line.substringBefore(':').substringAfter("Game ").toInt()
        val draws = line.substringAfter(':').splitToSequence(';').map { drawRaw ->
            var red = 0
            var green = 0
            var blue = 0
            for ((count, color) in drawRaw.splitToSequence(',', ' ').filter { it.isNotBlank() }.chunked(2)) {
                when(color) {
                    "red" -> red += count.toInt()
                    "green" -> green += count.toInt()
                    "blue" -> blue += count.toInt()
                }
            }
            Draw(red = red, blue = blue, green = green)
        }
        return Game(id = id, draws = draws.toList())
    }

}

fun main() {
    val solver = Day02()

    readFromClasspath("Day02.txt").useLines { lines ->
        val games = lines.map(solver::parseGame)
        val possibleGames = games.filter { game ->
            game.hasDrawPossibleWithBagCountsOf(red = 12, green = 13, blue = 14)
        }

        println("Part 1 solution: ${possibleGames.sumOf { it.id }}")
    }

    readFromClasspath("Day02.txt").useLines { lines ->
        val games = lines.map(solver::parseGame)
        val powers = games.map { game ->
            // TODO: refactor the classes to not create so many instances
            game.draws.fold(Draw()) { max, draw ->
                Draw(red = maxOf(max.red, draw.red), green = maxOf(max.green, draw.green), blue = maxOf(max.blue, draw.blue))
            }.run { red * green * blue }
        }

        println("Part 2 solution: ${powers.sum()}")
    }

}
