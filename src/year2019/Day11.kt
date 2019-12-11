package year2019

import year2019.util.*
import java.util.*
import kotlin.NoSuchElementException
import kotlin.RuntimeException
import kotlin.collections.HashMap

fun main(args: Array<String>) {
    Day11.a()
    Day11.b()
}

object Day11 {
    fun a() {
        val intCodes = ZeroDefaultList(getContents("day11.txt").split(",").map { it.toLong() }.toLongArray())
        val paintMap: MutableMap<Point, Long> = HashMap()
        val inputs = ArrayDeque<Long>()
        val outputs = ArrayDeque<Long>()
        var pointer = 0
        var robot = FaceablePoint()
        while (pointer != -1) {
            pointer = calculate(intCodes, inputs, outputs, pointer)
            while(outputs.isNotEmpty()) {
                paintMap[robot.toPoint()] = outputs.remove()
                when (outputs.remove()) {
                    0L -> robot = robot.turnLeft()
                    1L -> robot = robot.turnRight()
                }
                robot = robot.step()
            }
            inputs.add(paintMap.getOrDefault(robot, 0))
        }
        paintMap.size.print()
    }

    fun b() {
        val intCodes = ZeroDefaultList(getContents("day11.txt").split(",").map { it.toLong() }.toLongArray())
        val paintMap: MutableMap<Point, Long> = HashMap()
        paintMap[Point(0, 0)] = 1L
        val inputs = ArrayDeque<Long>()
        val outputs = ArrayDeque<Long>()
        var pointer = 0
        var robot = FaceablePoint()
        while (pointer != -1) {
            pointer = calculate(intCodes, inputs, outputs, pointer)
            while(outputs.isNotEmpty()) {
                paintMap[robot.toPoint()] = outputs.remove()
                when (outputs.remove()) {
                    0L -> robot = robot.turnLeft()
                    1L -> robot = robot.turnRight()
                }
                robot = robot.step()
            }
            inputs.add(paintMap.getOrDefault(robot.toPoint(), 0))
        }
        for (y in 0..6) {
            for (x in 0..50) {
                val charToPrint = when(paintMap.getOrDefault(Point(x, y), 0)) {
                    0L -> ' '
                    1L -> '█'
                    else -> throw RuntimeException("Invalid paint char")
                }
                print(charToPrint)
            }
            println()
        }
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