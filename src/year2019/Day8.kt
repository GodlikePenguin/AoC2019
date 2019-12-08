package year2019

import year2019.util.getContents
import year2019.util.print

fun main(args: Array<String>) {
    val width = 25
    val height = 6
    Day8.a(width, height)
    Day8.b(width, height)
}

object Day8 {
    fun a(width: Int, height: Int) {
        getContents("day8.txt")
                .chunked(width * height)
                .minBy { layer -> layer.count { it == '0' } }!!
                .let { layer -> layer.count { it == '1' } *  layer.count { it == '2' } }
                .print()
    }

    fun b(width: Int, height: Int) {
        val layers = getContents("day8.txt").chunked(width * height)
        val image: MutableMap<Int, Char> = HashMap() //use a map so we get putIfAbsent
        layers.forEach {
            layer -> layer.forEachIndexed { index, c -> if (c == '0' || c == '1') image.putIfAbsent(index, c) }
        }
        image.map {
                    when(it.value) {
                        '0' -> ' '
                        '1' -> '#'
                        else -> throw RuntimeException("Decoding failed")
                    }
                }
                .joinToString(separator = "")
                .chunked(width)
                .forEach { it.print() }
    }
}