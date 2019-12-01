package year2019.util

import java.io.File

fun getContents(fileName: String): String = File("./input/$fileName").readText(Charsets.UTF_8)

fun getLines(fileName: String): List<String> = File("./input/$fileName").readLines(Charsets.UTF_8)
