package year2019.util

import java.lang.RuntimeException
import kotlin.math.*

open class Point(val x: Int, val y: Int) {
    constructor(other: Point) : this(other.x, other.y)

    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    operator fun minus(other: Point) = Point(x - other.x, y - other.y)

    fun manhattan(other: Point) = abs(x - other.x) + abs(y - other.y)

    fun distance(other: Point) = sqrt(((other.y - y) * (other.y - y) + (other.x - x) * (other.x - x)).toDouble())

    fun angle(target: Point) = atan2((target.x - x).toDouble(), (target.y - y).toDouble())

    fun up(amount: Int = 1) = Point(x, y - amount)
    fun down(amount: Int = 1) = Point(x, y + amount)
    fun left(amount: Int = 1) = Point(x - amount, y)
    fun right(amount: Int = 1) = Point(x + amount, y)

    override fun equals(other: Any?): Boolean {
        if (other is Point) {
            return (this.x == other.x && this.y == other.y)
        }
        return false
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }

    override fun toString(): String {
        return "Point(x=${this.x}, y=${this.y})"
    }

}

fun Pair<Int, Int>.toPoint() = Point(this.first, this.second)

val Direction = mapOf(0 to "LEFT", 1 to "UP", 2 to "RIGHT", 3 to "DOWN")

open class FaceablePoint(x: Int = 0, y: Int = 0, val facing: Int = 1) : Point(x, y) {
    constructor(point: Point, f: Int) : this(point.x, point.y, f)

    fun step(): FaceablePoint {
        return when(Direction[facing]) {
            "LEFT" -> FaceablePoint(left(), this.facing)
            "UP" -> FaceablePoint(up(), this.facing)
            "RIGHT" -> FaceablePoint(right(), this.facing)
            "DOWN" -> FaceablePoint(down(), this.facing)
            else -> throw RuntimeException("Trying to step in invalid direction")
        }
    }

    fun turnLeft() = FaceablePoint(x, y, facing = Math.floorMod((facing - 1), Direction.size))
    fun turnRight() = FaceablePoint(x, y, facing = Math.floorMod((facing + 1), Direction.size))

    fun toPoint() = Point(this.x, this.y)

    override fun toString(): String {
        return "FaceablePoint(x=${this.x}, y=${this.y}, facing=${this.facing})"
    }
}
