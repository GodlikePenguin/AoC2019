package year2019

import year2019.util.getLines
import year2019.util.print
import kotlin.math.abs

fun main(args: Array<String>) {
    Day10.a()
    Day10.b()
}

object Day10 {
    fun a() {
        val asteroids: MutableList<Pair<Int, Int>> = ArrayList()
        getLines("day10.txt").forEachIndexed { row, line -> line.forEachIndexed { column, digit -> if (digit == '#') asteroids.add(Pair(column, row))} }
        val visibleAsteroids: MutableMap<Pair<Int, Int>, Int> = HashMap()
        for (first in asteroids) {
            var canSee = 0
            for (second in asteroids) {
                if (first == second) continue
                var canBeSeen = true
                for (obstacle in asteroids) {
                    if (obstacle == first || obstacle == second) continue
                    if (isInbetween(obstacle, first, second)) {
                        canBeSeen = false
                    }
                }
                if (canBeSeen) canSee++
            }
            visibleAsteroids[first] = canSee
        }
        visibleAsteroids.maxBy { it.value }!!.print()
    }

    fun b() {
        val asteroids: MutableMap<Pair<Int, Int>, Boolean> = HashMap()
        getLines("day10.txt").forEachIndexed { row, line -> line.forEachIndexed { column, digit -> if (digit == '#') asteroids[Pair(column, row)] = true } }
        val base = Pair(37, 25) //from the last part
    }

    fun isInbetween(obstacle: Pair<Int, Int>, start: Pair<Int, Int>, end: Pair<Int, Int>): Boolean {
        var dx = end.first - start.first
        var dy = end.second - start.second
        val greatestDivisor = gcd(abs(dx), abs(dy))
        dx /= greatestDivisor
        dy /= greatestDivisor
        var checkPoint = Pair(start.first + dx, start.second + dy)
        while(checkPoint != end) {
            if (checkPoint == obstacle) return true
            checkPoint = Pair(checkPoint.first + dx, checkPoint.second + dy)
        }
        return false
    }

    fun gcd(a: Int, b: Int): Int {
        if (b == 0) return a
        return gcd(b, a.rem(b))
    }

}