package year2019.util

import kotlin.math.*

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    operator fun minus(other: Point) = Point(x - other.x, y - other.y)

    fun manhattan(other: Point) = abs(x - other.x) + abs(y - other.y)

    fun distance(other: Point) = sqrt(((other.y - y) * (other.y - y) + (other.x - x) * (other.x - x)).toDouble())

    fun angle(target: Point) = atan2((target.x - x).toDouble(), (target.y - y).toDouble())

    fun down(amount: Int = 1) = copy(y = y + amount)
    fun up(amount: Int = 1) = copy(y = y - amount)
    fun left(amount: Int = 1) = copy(x = x - amount)
    fun right(amount: Int = 1) = copy(x = x + amount)

}

fun Pair<Int, Int>.toPoint() = Point(this.first, this.second)
