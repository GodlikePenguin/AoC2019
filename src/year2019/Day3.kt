package year2019

import year2019.util.getLines
import year2019.util.print
import kotlin.math.abs

fun main(args: Array<String>) {
    Day3.a()
    Day3.b()
}

object Day3 {
    fun a() {
        val lines = getLines("day3.txt")
        val board: MutableMap<Pair<Int, Int>, MutableSet<Int>> = HashMap()
        for (lineKey in lines.indices) {
            var x = 0; var y = 0
            var dx = 0; var dy = 0
            for (move in lines[lineKey].split(",")) {
                when (move[0]) {
                    'U' -> {
                        dx = 0
                        dy = 1
                    }
                    'D' -> {
                        dx = 0
                        dy = -1
                    }
                    'L' -> {
                        dx = -1
                        dy = 0
                    }
                    'R' -> {
                        dx = 1
                        dy = 0
                    }
                }
                for (i in 1..move.substring(1).toInt()) {
                    x += dx
                    y += dy
                    val newSet = board.getOrDefault(Pair(x, y), HashSet())
                    newSet.add(lineKey)
                    board[Pair(x, y)] = newSet
                }
            }
        }
        board.filter { it.value.size > 1 }.minBy { abs(it.key.first) + abs(it.key.second) }!!.key.toList().sum().print()
    }

    fun b() {
        val lines = getLines("day3.txt")
        val board: MutableMap<Pair<Int, Int>, MutableMap<Int, Int>> = HashMap()
        for (lineKey in lines.indices) {
            var x = 0; var y = 0
            var dx = 0; var dy = 0
            var totalMoves = 0
            for (move in lines[lineKey].split(",")) {
                when (move[0]) {
                    'U' -> {
                        dx = 0
                        dy = 1
                    }
                    'D' -> {
                        dx = 0
                        dy = -1
                    }
                    'L' -> {
                        dx = -1
                        dy = 0
                    }
                    'R' -> {
                        dx = 1
                        dy = 0
                    }
                }
                for (i in 1..move.substring(1).toInt()) {
                    totalMoves++
                    x += dx
                    y += dy
                    val newInnerMap = board.getOrDefault(Pair(x, y), HashMap())
                    newInnerMap.putIfAbsent(lineKey, totalMoves)
                    board[Pair(x, y)] = newInnerMap
                }
            }
        }
        board.filter { it.value.size > 1 }.minBy { it.value.values.sum() }!!.value.values.sum().print()
    }
}