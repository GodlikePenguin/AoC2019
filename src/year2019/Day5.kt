package year2019

import year2019.util.getContents
import year2019.util.print
import java.lang.RuntimeException


fun main(args: Array<String>) {
    Day5.a()
    Day5.b()
}

object Day5 {
    fun a() {
        val codes = getContents("day5.txt")
                .split(",")
                .map { it.toInt() }
                .toIntArray()

        calculate(codes)
    }

    fun b() {

    }

    fun calculate(codes: IntArray) {
        var instructionPointer = 0
        loop@ while(instructionPointer < codes.size) {
            val currentInstruction = codes[instructionPointer].toString().padStart(5, '0')
            var first = 0; var second = 0
            try {
                first = if (currentInstruction[2] == '1') codes[instructionPointer+1] else codes[codes[instructionPointer+1]]
                second = if (currentInstruction[1] == '1') codes[instructionPointer+2] else codes[codes[instructionPointer+2]]
            } catch (e: ArrayIndexOutOfBoundsException) {
                //There isn't enough instructions ahead to grab the first and second, just ignore
            }
            when (currentInstruction.substring(3)) { //get last two digits
                "01" -> {
                    codes[codes[instructionPointer+3]] = first + second
                    instructionPointer += 4
                }
                "02" -> {
                    codes[codes[instructionPointer+3]] = first * second
                    instructionPointer += 4
                }
                "03" -> {
                    codes[codes[instructionPointer+1]] = 1 //input is always 1
                    instructionPointer += 2
                }
                "04" -> {
                    first.print()
                    instructionPointer += 2
                }
                "99" -> {
                    break@loop
                }
                else -> {
                    throw RuntimeException("We fucked up")
                }
            }
        }
    }
}