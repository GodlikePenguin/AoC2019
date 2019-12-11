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
        val state = StateHolder()
        var done = false
        var robot = FaceablePoint()
        while (!done) {
            done = calculate(intCodes, inputs, outputs, state)
            while(outputs.isNotEmpty()) {
                paintMap[robot] = outputs.remove()
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
        val state = StateHolder()
        var done = false
        var robot = FaceablePoint()
        while (!done) {
            done = calculate(intCodes, inputs, outputs, state)
            while(outputs.isNotEmpty()) {
                paintMap[robot] = outputs.remove()
                when (outputs.remove()) {
                    0L -> robot = robot.turnLeft()
                    1L -> robot = robot.turnRight()
                }
                robot = robot.step()
            }
            inputs.add(paintMap.getOrDefault(robot, 0))
        }
        for (y in paintMap.keys.minBy { it.y }!!.y..paintMap.keys.maxBy { it.y }!!.y) {
            for (x in paintMap.keys.minBy { it.x }!!.x..paintMap.keys.maxBy { it.x }!!.x) {
                val charToPrint = when(paintMap.getOrDefault(Point(x, y), 0)) {
                    0L -> ' '
                    1L -> '#'
                    else -> throw RuntimeException("Invalid paint char")
                }
                print(charToPrint)
            }
            println()
        }
    }

    fun calculate(codes: ZeroDefaultList, inputs: Queue<Long>, outputs: Queue<Long>, state: StateHolder): Boolean {
        loop@ while(state.instructionPointer < codes.size) {
            val currentInstruction = codes[state.instructionPointer].toString().padStart(5, '0')
            val first = if (currentInstruction[2] == '1') codes[state.instructionPointer+1] else if (currentInstruction[2] == '2') codes[state.relativeBase + codes[state.instructionPointer+1]] else codes[codes[state.instructionPointer+1]]
            val second = if (currentInstruction[1] == '1') codes[state.instructionPointer+2] else if (currentInstruction[1] == '2') codes[state.relativeBase + codes[state.instructionPointer+2]] else codes[codes[state.instructionPointer+2]]
            val calculatedThirdOffset = if (currentInstruction[0] == '0') 0 else if (currentInstruction[0] == '2') state.relativeBase else throw RuntimeException("Output index cannot be immediate")
            when (currentInstruction.substring(3)) { //get last two digits
                "01" -> {
                    codes[codes[state.instructionPointer+3] + calculatedThirdOffset] = first + second
                    state.instructionPointer += 4
                }
                "02" -> {
                    codes[codes[state.instructionPointer+3] + calculatedThirdOffset] = first * second
                    state.instructionPointer += 4
                }
                "03" -> {
                    try {
                        codes[codes[state.instructionPointer + 1] +  if (currentInstruction[2] == '0') 0 else if (currentInstruction[2] == '2') state.relativeBase else throw RuntimeException("Output index cannot be immediate")] = inputs.remove()
                        state.instructionPointer += 2
                    } catch(e: NoSuchElementException) {
                        return false
                    }
                }
                "04" -> {
                    outputs.add(first)
                    state.instructionPointer += 2
                }
                "05" -> {
                    state.instructionPointer = if (first != 0L) second.toInt() else state.instructionPointer + 3
                }
                "06" -> {
                    state.instructionPointer = if (first == 0L) second.toInt() else state.instructionPointer + 3
                }
                "07" -> {
                    codes[codes[state.instructionPointer+3] + calculatedThirdOffset] = if (first < second) 1L else 0L
                    state.instructionPointer += 4
                }
                "08" -> {
                    codes[codes[state.instructionPointer+3] + calculatedThirdOffset] = if (first == second) 1L else 0L
                    state.instructionPointer += 4
                }
                "09" -> {
                    state.relativeBase += first
                    state.instructionPointer += 2
                }
                "99" -> {
                    return true
                }
                else -> {
                    throw RuntimeException("We fucked up")
                }
            }
        }
        throw RuntimeException("We shouldn't be here")
    }
}

data class StateHolder(var instructionPointer: Int = 0, var relativeBase: Long = 0L)