package year2019

import year2019.util.Point
import year2019.util.getLines
import year2019.util.print
import kotlin.math.PI

fun main(args: Array<String>) {
    Day10.a()
    Day10.b()
}

object Day10 {
    fun a() {
        val asteroids = getLines("day10.txt").mapIndexed {
            line, str -> str.mapIndexed { col, c -> if (c == '#') Point(col, line) else null }
        }.flatten().filterNotNull()
        asteroids.map { it to it.visible(asteroids) }.maxBy { it.second }!!.print()
    }

    fun b() {
        val asteroids = getLines("day10.txt").mapIndexed {
            line, str -> str.mapIndexed { col, c -> if (c == '#') Point(col, line) else null }
        }.flatten().filterNotNull()
        val laser = asteroids.map { it to it.visible(asteroids) }.maxBy { it.second }!!.first
        val asteroidsAtRadians = asteroids.filterNot { it == laser } //get all asteroids except for the laser
                .groupBy { laser.angle(it) + PI } //put them into groups based on their angle to the laser, add PI to offset the angle so we start facing up like the question wants
                .map { group -> group.key to group.value.sortedBy { asteroid -> laser.distance(asteroid) }.toMutableList() } //sort each group based on distance to the laser
                .sortedByDescending { it.first } //sort the map in clockwise order of radians around the laser

        var lastAsteroidDestroyed = Point(0, 0)

        for (i in 0..199) {
            val asteroidsAtCurrentAngle = asteroidsAtRadians[i % asteroidsAtRadians.size].second

            if (asteroidsAtCurrentAngle.isNotEmpty()) {
                lastAsteroidDestroyed = asteroidsAtCurrentAngle.removeAt(0)
            }
        }

        (lastAsteroidDestroyed.x * 100 + lastAsteroidDestroyed.y).print()
    }

    fun Point.visible(points: List<Point>)= points.filterNot { it == this }.map { it.angle(this) }.distinct().size


}