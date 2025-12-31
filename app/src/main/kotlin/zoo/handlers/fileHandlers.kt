package handlers

import java.io.File


val bySpaces = Regex("\\s+")
/**
 * Parses a text file and returns a list of non-blank words.
 */
fun splitFile(path: String, splitBy: Regex = bySpaces): List<String> =
    File(path).useLines { lines ->
        lines.flatMap{ line-> line.split(splitBy) }
            .filter { word -> word.isNotBlank() }
            .toList()
    }