package year2019

import year2019.util.*
import kotlin.math.min

fun main(args: Array<String>) {
    Day12.a()
    Day12.b()
}

object Day12 {
    fun a() {
        val planets: List<Planet> = getLines("day12.txt").map { it.removePrefix("<").removeSuffix(">") }
                .map { it.split(", ").map { exp -> exp.split("=")[1].toInt() } }
                .map { Planet(Vector(it[0], it[1], it[2]), Vector(0,0,0)) }
        for (i in 1..1000) {
            for (planet in planets) {
                for (other in planets.filterNot { it == planet }) {
                    val xdiff = planet.position.x.compareTo(other.position.x) * -1
                    val ydiff = planet.position.y.compareTo(other.position.y) * -1
                    val zdiff = planet.position.z.compareTo(other.position.z) * -1
                    planet.velocity = planet.velocity + Vector(xdiff, ydiff, zdiff)
                }
            }
            for (planet in planets) {
                planet.update()
            }
        }
        planets.map { it.position.abs() * it.velocity.abs() }.sum().print()
    }

    fun b() {
        val planets: List<Planet> = getLines("day12.txt").map { it.removePrefix("<").removeSuffix(">") }
                .map { it.split(", ").map { exp -> exp.split("=")[1].toInt() } }
                .map { Planet(Vector(it[0], it[1], it[2]), Vector(0,0,0)) }
        val originalPlanets = planets.map { it.copy() }

        var iterations = 0
        var xLoop = Int.MAX_VALUE
        var yLoop = Int.MAX_VALUE
        var zLoop = Int.MAX_VALUE
        while (true) {
            iterations++
            for (planet in planets) {
                for (other in planets.filterNot { it == planet }) {
                    val xdiff = planet.position.x.compareTo(other.position.x) * -1
                    val ydiff = planet.position.y.compareTo(other.position.y) * -1
                    val zdiff = planet.position.z.compareTo(other.position.z) * -1
                    planet.velocity = planet.velocity + Vector(xdiff, ydiff, zdiff)
                }
            }
            var backToInitialXCount = 0
            var backToInitialYCount = 0
            var backToInitialZCount = 0
            for (planet in planets.withIndex()) {
                planet.value.update()
                if (planet.value.hasSameX(originalPlanets[planet.index])) {
                    backToInitialXCount++
                }
                if (planet.value.hasSameY(originalPlanets[planet.index])) {
                    backToInitialYCount++
                }
                if (planet.value.hasSameZ(originalPlanets[planet.index])) {
                    backToInitialZCount++
                }
            }
            if (backToInitialXCount == 4) xLoop = min(xLoop, iterations)
            if (backToInitialYCount == 4) yLoop = min(yLoop, iterations)
            if (backToInitialZCount == 4) zLoop = min(zLoop, iterations)
            if (xLoop < Int.MAX_VALUE && yLoop < Int.MAX_VALUE && zLoop < Int.MAX_VALUE) {
                break
            }
        }
        arrayLCM(xLoop, yLoop, zLoop).print()
    }
}