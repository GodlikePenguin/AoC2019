package year2019.util

import java.io.File

fun getContents(fileName: String): String = File("./input/$fileName").readText(Charsets.UTF_8)
