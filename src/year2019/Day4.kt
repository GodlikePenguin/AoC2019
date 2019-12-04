package year2019

import year2019.util.getContents
import year2019.util.print

fun main(args: Array<String>) {
    Day4.a()
    Day4.b()
}

object Day4 {
    fun a() {
        val (lower, upper) = getContents("day4.txt").split("-").map { it.toInt() }
        var valid = 0
        for (i in lower..upper) {
            val iString = i.toString()
            var hasRepeatedDigits = false
            var isAllAscending = true
            for (index in 0..iString.length-2) {
                if (iString[index] == iString[index+1]) hasRepeatedDigits = true
                if (iString[index] > iString[index+1]) isAllAscending = false
            }
            if (hasRepeatedDigits && isAllAscending) valid++
        }
        valid.print()
    }

    fun b() {
        val (lower, upper) = getContents("day4.txt").split("-").map { it.toInt() }
        var valid = 0
        for (i in lower..upper) {
            val iString = i.toString()
            var hasRepeatedDigits = false
            var isAllAscending = true
            for (index in 0..iString.length-2) {
                if (iString[index] == iString[index+1]) hasRepeatedDigits = true
                if (iString[index] > iString[index+1]) isAllAscending = false
            }
            if (hasRepeatedDigits && isAllAscending) {
                var exactly2Repeats: MutableMap<Int, Boolean> = HashMap()
                for (substr in iString.windowed(3)) {
                    if (substr[0] == substr[1] && substr[1] != substr[2]) exactly2Repeats.putIfAbsent(Character.getNumericValue(substr[0]), true) //True can't overwrite false
                    if (substr[0] != substr[1] && substr[1] == substr[2]) exactly2Repeats.putIfAbsent(Character.getNumericValue(substr[1]), true)
                    if (substr[0] == substr[1] && substr[1] == substr[2]) exactly2Repeats[Character.getNumericValue(substr[0])] = false //false can overwrite true
                }
                if (exactly2Repeats.filterValues { it }.isNotEmpty()) valid++
            }
        }
        valid.print()
    }
}