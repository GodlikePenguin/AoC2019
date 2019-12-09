package year2019

import year2019.util.ZeroDefaultList
import year2019.util.getContents
import year2019.util.print
import java.lang.RuntimeException
import java.util.*
import kotlin.NoSuchElementException

fun main(args: Array<String>) {
    Day9.a()
    Day9.b()
}

object Day9 {
    fun a() {
        val codes = ZeroDefaultList(getContents("day9.txt").split(",").map { it.toLong() }.toLongArray())
        val inputs = ArrayDeque<Long>().also { it.add(1) }
        val outputs = ArrayDeque<Long>()
        calculate(codes, inputs, outputs, 0)
        outputs.print()
    }

    fun b() {
        val codes = ZeroDefaultList(getContents("day9.txt").split(",").map { it.toLong() }.toLongArray())
        val inputs = ArrayDeque<Long>().also { it.add(2) }
        val outputs = ArrayDeque<Long>()
        calculate(codes, inputs, outputs, 0)
        outputs.print()
    }

    fun calculate(codes: ZeroDefaultList, inputs: Queue<Long>, outputs: Queue<Long>, startPointer: Int): Int {
        var instructionPointer = startPointer
        var relativeBase = 0L
        loop@ while(instructionPointer < codes.size) {
            val currentInstruction = codes[instructionPointer].toString().padStart(5, '0')
            val first = if (currentInstruction[2] == '1') codes[instructionPointer+1] else if (currentInstruction[2] == '2') codes[relativeBase + codes[instructionPointer+1]] else codes[codes[instructionPointer+1]]
            val second = if (currentInstruction[1] == '1') codes[instructionPointer+2] else if (currentInstruction[1] == '2') codes[relativeBase + codes[instructionPointer+2]] else codes[codes[instructionPointer+2]]
            val calculatedThirdOffset = if (currentInstruction[0] == '0') 0 else if (currentInstruction[0] == '2') relativeBase else throw RuntimeException("Output index cannot be immediate")
            when (currentInstruction.substring(3)) { //get last two digits
                "01" -> {
                    codes[codes[instructionPointer+3] + calculatedThirdOffset] = first + second
                    instructionPointer += 4
                }
                "02" -> {
                    codes[codes[instructionPointer+3] + calculatedThirdOffset] = first * second
                    instructionPointer += 4
                }
                "03" -> {
                    try {
                        codes[codes[instructionPointer + 1] +  if (currentInstruction[2] == '0') 0 else if (currentInstruction[2] == '2') relativeBase else throw RuntimeException("Output index cannot be immediate")] = inputs.remove()
                        instructionPointer += 2
                    } catch(e: NoSuchElementException) {
                        return instructionPointer
                    }
                }
                "04" -> {
                    outputs.add(first)
                    instructionPointer += 2
                }
                "05" -> {
                    instructionPointer = if (first != 0L) second.toInt() else instructionPointer + 3
                }
                "06" -> {
                    instructionPointer = if (first == 0L) second.toInt() else instructionPointer + 3
                }
                "07" -> {
                    codes[codes[instructionPointer+3] + calculatedThirdOffset] = if (first < second) 1L else 0L
                    instructionPointer += 4
                }
                "08" -> {
                    codes[codes[instructionPointer+3] + calculatedThirdOffset] = if (first == second) 1L else 0L
                    instructionPointer += 4
                }
                "09" -> {
                    relativeBase += first
                    instructionPointer += 2
                }
                "99" -> {
                    return -1
                }
                else -> {
                    throw RuntimeException("We fucked up")
                }
            }
        }
        throw RuntimeException("We shouldn't be here")
    }
}