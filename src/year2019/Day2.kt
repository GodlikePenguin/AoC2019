package year2019

import year2019.util.getContents
import year2019.util.print
import java.lang.RuntimeException


fun main(args: Array<String>) {
    Day2.a()
    Day2.b()
}

object Day2 {
    fun a() {
        val codes = getContents("day2.txt")
                .split(",")
                .map { it.toInt() }
                .toIntArray()

        codes[1] = 12
        codes[2] = 2
        calculate(codes).print()
    }

    fun b() {
        val initialCodes = getContents("day2.txt")
                .split(",")
                .map { it.toInt() }
                .toIntArray()

        for (noun in 0..99) {
            for (verb in 0..99) {
                val attempt = initialCodes.copyOf()
                attempt[1] = noun
                attempt[2] = verb
                if (calculate(attempt) == 19690720) {
                    (100 * noun + verb).print()
                    break
                }
            }
        }
    }

    fun calculate(codes: IntArray): Int {
        for (i in 0..codes.size step 4) {
            if (codes[i] == 1) {
                codes[codes[i+3]] = codes[codes[i+1]] + codes[codes[i+2]]
            } else if (codes[i] == 2) {
                codes[codes[i+3]] = codes[codes[i+1]] * codes[codes[i+2]]
            } else if (codes[i] == 99) {
                break
            } else {
                throw RuntimeException("We fucked up")
            }
        }
        return codes[0]
    }
}