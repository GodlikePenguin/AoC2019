package year2019

import year2019.util.Point
import year2019.util.ZeroDefaultList
import year2019.util.getContents
import year2019.util.print
import java.util.*
import kotlin.NoSuchElementException
import kotlin.collections.HashMap

fun main(args: Array<String>) {
    Day13.a()
    Day13.b()
}

var score = 0L

object Day13 {
    fun a() {
        val intCodes = ZeroDefaultList(getContents("day13.txt").split(",").map { it.toLong() }.toLongArray())
        val inputs = ArrayDeque<Long>()
        val outputs = ArrayDeque<Long>()
        val state = StateHolder()
        calculate(intCodes, inputs, outputs, state)
        outputs.chunked(3).filter { it[2] == 2L }.count().print()
    }

    fun b() {
        val intCodes = ZeroDefaultList(getContents("day13.txt").split(",").map { it.toLong() }.toLongArray())
        intCodes[0] = 2
        val inputs = ArrayDeque<Long>()
        val outputs = ArrayDeque<Long>()
        val state = StateHolder()
        var isDone = calculate(intCodes, inputs, outputs, state)
        var game = HashMap<Point, GameObject>()
        updateGameBoard(game, outputs)
        while (!isDone) {
            val b = getPointOfBall(game)
            val p = getPointOfPaddle(game)
            if (b.x > p.x) inputs.add(1)
            if (b.x < p.x) inputs.add(-1)
            if (b.x == p.x) inputs.add(0)
            isDone = calculate(intCodes, inputs, outputs, state)
            updateGameBoard(game, outputs)
//            printGameBoard(game)
        }
        score.print()
    }

    private fun updateGameBoard(game: MutableMap<Point, GameObject>, outputs: ArrayDeque<Long>) {
        while(outputs.isNotEmpty()) {
            val x = outputs.remove()
            val y = outputs.remove()
            val objectKey = outputs.remove()
            val obj: GameObject = when(objectKey) {
                0L -> GameObject.EMPTY
                1L -> GameObject.WALL
                2L -> GameObject.BLOCK
                3L -> GameObject.PADDLE
                4L -> GameObject.BALL
                else -> {
                    if (x == -1L && y == 0L) {
                        score = objectKey
                        GameObject.EMPTY
                    } else {
                        throw RuntimeException("Invalid Game Object: $objectKey")
                    }
                }
            }
            game.put(Point(x.toInt(), y.toInt()), obj)
        }
    }

    fun printGameBoard(game: MutableMap<Point, GameObject>) {
        for (y in game.keys.minBy { it.y }!!.y..game.keys.maxBy { it.y }!!.y) {
            for (x in game.keys.minBy { it.x }!!.x..game.keys.maxBy { it.x }!!.x) {
                val go = game.getOrDefault(Point(x, y), GameObject.EMPTY)
                print(go)
            }
            println()
        }
    }

    fun getPointOfBall(game: MutableMap<Point, GameObject>): Point {
        val balls = game.filterValues { it == GameObject.BALL }
        if (balls.size != 1) throw java.lang.RuntimeException("Could not get single ball from the game map")
        return balls.entries.first().key
    }

    fun getPointOfPaddle(game: MutableMap<Point, GameObject>): Point {
        val paddles = game.filterValues { it == GameObject.PADDLE }
        if (paddles.size != 1) throw java.lang.RuntimeException("Could not get single paddle from the game map")
        return paddles.entries.first().key
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

enum class GameObject(val icon: Char) {
    WALL('.'), BLOCK('#'), PADDLE('_'), BALL('o'), EMPTY(' ');

    override fun toString(): String {
        return this.icon.toString()
    }
}