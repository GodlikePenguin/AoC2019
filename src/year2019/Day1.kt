package year2019

import year2019.util.getContents
import year2019.util.getLines

fun main(args: Array<String>) {
    Day1.a()
    Day1.b()
}

private object Day1 {
    fun a() {
        println(getLines("day1a.txt")
                .map { it.toInt() }
                .map { it / 3 }
                .map { Math.floor(it.toDouble()) }
                .map { it - 2 }
                .sum())
}

    fun b() {
        println(getLines("day1a.txt")
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
                .sum())
    }

}