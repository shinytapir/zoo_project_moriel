package app

import java.io.File

private val noSpaces = Regex("\\s+")

/**
 * Parses a text file and returns a list of non-blank words.
 */
fun parseFile(path: String): List<String> =
    File(path).useLines { lines ->
        lines.flatMap { line -> line.split(noSpaces) }
            .filter { word -> word.isNotBlank() }
            .toList()
    }