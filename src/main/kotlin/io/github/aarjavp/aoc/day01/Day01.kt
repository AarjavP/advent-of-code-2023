package io.github.aarjavp.aoc.day01

import io.github.aarjavp.aoc.readFromClasspath

class Day01 {

    fun printFirstLine(lines: Sequence<String>) {
        lines.firstOrNull()?.let { println(it) }
    }

}

fun main() {
    val solver = Day01()

    readFromClasspath("Day01.txt").useLines { lines ->
        solver.printFirstLine(lines)
    }

}
