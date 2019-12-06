package year2019

import year2019.util.TreeNode
import year2019.util.getLines
import year2019.util.print

fun main(args: Array<String>) {
    Day6.a()
    Day6.b()
}

object Day6 {
    fun a() {
        val input = getLines("day6.txt")
        populatePlanets(input).toList().sumBy { it.second.calculateDepth() }.print()
    }

    fun b() {
        val input = getLines("day6.txt")
        val planets = populatePlanets(input)
        val me = planets.getValue("YOU")
        val santa = planets.getValue("SAN")
        val deepestParent = me.getChain().intersect(santa.getChain()).maxBy { it.calculateDepth() }
        val meDepth = me.calculateDepth() - 1
        val santaDepth = santa.calculateDepth() - 1
        val deepestParentDepth = deepestParent!!.calculateDepth()
        ((meDepth - deepestParentDepth) + (santaDepth - deepestParentDepth)).print()
    }

    private fun populatePlanets(input: List<String>): MutableMap<String, TreeNode<String>> {
        val planets: MutableMap<String, TreeNode<String>> = HashMap()
        for (line in input) {
            val (labelA, labelB) = line.split(")")
            val planetA = planets.getOrPut(labelA, { TreeNode(labelA) })
            val planetB = planets.getOrPut(labelB, { TreeNode(labelB) })
            planetA.addChild(planetB)
        }
        return planets
    }
}