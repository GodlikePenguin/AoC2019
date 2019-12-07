package year2019

import year2019.util.getContents
import year2019.util.print
import java.lang.RuntimeException
import java.util.*
import kotlin.NoSuchElementException


fun main(args: Array<String>) {
    Day7.a()
    Day7.b()
}

object Day7 {
    fun a() {
        val originalCodes = getContents("day7.txt")
                .split(",")
                .map { it.toInt() }
                .toIntArray()

        val thrusterValues: MutableMap<String, Int> = HashMap()
        for (ampA in 0..4) {
            for (ampB in 0..4) {
                for (ampC in 0..4) {
                    for (ampD in 0..4) {
                        for (ampE in 0..4) {
                            thrusterValues["$ampA$ampB$ampC$ampD$ampE"] = calculate(originalCodes.copyOf(), ampE,
                                    calculate(originalCodes.copyOf(), ampD,
                                            calculate(originalCodes.copyOf(), ampC,
                                                    calculate(originalCodes.copyOf(), ampB,
                                                            calculate(originalCodes.copyOf(), ampA, 0)))))
                        }
                    }
                }
            }
        }
        thrusterValues.filter { it.key.toSet().size == 5 }.maxBy { it.value }!!.value.print()
    }

    fun b() {
        val originalCodes = getContents("day7.txt")
                .split(",")
                .map { it.toInt() }
                .toIntArray()

        val thrusterValues: MutableMap<String, Int> = HashMap()
        val amps: Array<Amp> = Array(5) {Amp(intArrayOf(), ArrayDeque(), 0)}
        for (phaseA in 5..9) {
            for (phaseB in 5..9) {
                for (phaseC in 5..9) {
                    for (phaseD in 5..9) {
                        for (phaseE in 5..9) {
                            amps[0] = Amp(originalCodes.copyOf())
                            amps[0].inputs.add(phaseA)
                            amps[0].inputs.add(0)

                            amps[1] = Amp(originalCodes.copyOf())
                            amps[1].inputs.add(phaseB)

                            amps[2] = Amp(originalCodes.copyOf())
                            amps[2].inputs.add(phaseC)

                            amps[3] = Amp(originalCodes.copyOf())
                            amps[3].inputs.add(phaseD)

                            amps[4] = Amp(originalCodes.copyOf())
                            amps[4].inputs.add(phaseE)
                            while(amps[4].instructionPointer != -1) {
                                for (i in amps.indices) {
                                    amps[i].instructionPointer = calculateB(amps[i].state, amps[i].inputs, amps[(i+1)%5].inputs, amps[i].instructionPointer)
                                }
                            }
                            thrusterValues["$phaseA$phaseB$phaseC$phaseD$phaseE"] = amps[0].inputs.last()
                        }
                    }
                }
            }
        }
        thrusterValues.filter { it.key.toSet().size == 5 }.maxBy { it.value }!!.value.print()
    }

    fun calculate(codes: IntArray, phaseSetting: Int, input: Int): Int {
        var instructionPointer = 0
        var inputCounter = 0
        var lastOutput: Int? = null
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
                    codes[codes[instructionPointer+1]] = (if (inputCounter == 0) phaseSetting else input)
                    inputCounter++
                    instructionPointer += 2
                }
                "04" -> {
                    lastOutput = first
                    instructionPointer += 2
                }
                "05" -> {
                    instructionPointer = if (first != 0) second else instructionPointer + 3
                }
                "06" -> {
                    instructionPointer = if (first == 0) second else instructionPointer + 3
                }
                "07" -> {
                    codes[codes[instructionPointer+3]] = if (first < second) 1 else 0
                    instructionPointer += 4
                }
                "08" -> {
                    codes[codes[instructionPointer+3]] = if (first == second) 1 else 0
                    instructionPointer += 4
                }
                "99" -> {
                    break@loop
                }
                else -> {
                    throw RuntimeException("We fucked up")
                }
            }
        }
        return lastOutput!!
    }

    fun calculateB(codes: IntArray, inputs: Queue<Int>, outputs: Queue<Int>, startPointer: Int): Int {
        var instructionPointer = startPointer
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
                    try {
                        codes[codes[instructionPointer + 1]] = inputs.remove()
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
                    instructionPointer = if (first != 0) second else instructionPointer + 3
                }
                "06" -> {
                    instructionPointer = if (first == 0) second else instructionPointer + 3
                }
                "07" -> {
                    codes[codes[instructionPointer+3]] = if (first < second) 1 else 0
                    instructionPointer += 4
                }
                "08" -> {
                    codes[codes[instructionPointer+3]] = if (first == second) 1 else 0
                    instructionPointer += 4
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

data class Amp(var state: IntArray, var inputs: Queue<Int> = ArrayDeque(), var instructionPointer: Int = 0)