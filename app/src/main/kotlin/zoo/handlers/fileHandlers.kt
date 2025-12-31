package handlers

import java.io.File

/** Default regex to split text by one or more whitespace characters. */
private val DEFAULT_BY_SPACES_REGEX = Regex("\\s+")

/**
 * Reads a text file and returns a list of non-blank words split by the specified regex.(or the default)
 *
 * @param path The path to the text file.
 * @param splitBy A [Regex] used to split each line into words. Defaults to whitespace.
 * @return A [List] of non-blank words in the file.
 */
fun splitFile(path: String, splitBy: Regex = DEFAULT_BY_SPACES_REGEX): List<String> =
    File(path).useLines { lines ->
        lines
            .flatMap { line -> line.split(splitBy) }
            .filter { word -> word.isNotBlank() }
            .toList()
    }
