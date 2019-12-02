package year2019

import year2019.util.getLines
import year2019.util.print

fun main(args: Array<String>) {
    Day1.a()
    Day1.b()
}

private object Day1 {
    fun a() {
        getLines("day1.txt")
                .map { it.toInt() }
                .map { it / 3 }
                .map { Math.floor(it.toDouble()) }
                .map { it - 2 }
                .sum()
                .print()
}

    fun b() {
        getLines("day1.txt")
                .map { it.toInt() }
                .map {
                    var tot = 0
                    var massLeft = it
                    while (true) {
                        massLeft = (massLeft / 3) - 2
                        if (massLeft < 0) break
                        tot += massLeft
                    }
                    tot
                }
                .sum()
                .print()
    }

}