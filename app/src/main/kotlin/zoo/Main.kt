package zoo

import java.io.File
import java.io.IOException
import kotlinx.cli.*



fun parseArguments(arguments: Array<String>): Pair<String, String> {
    val parser = ArgParser("animals")

    val animalsFile by parser.option(
        ArgType.String,
        shortName = "a",
        description = "Animals file"
    ).required()

    val propertiesFile by parser.option(
        ArgType.String,
        shortName = "p",
        description = "Properties file"
    ).required()

    parser.parse(arguments)
    return animalsFile to propertiesFile
}

fun parseFile(fileName: String): List<String> {
    val regex = "\\s+".toRegex()

    val words: List<String> = File(fileName).useLines { lines ->
        lines
            .flatMap { line -> line.split(regex) }
            .filter { it.isNotBlank() }
            .toList()
    }

    return words
}


fun printAnimals(animalNames: List<String>) {
    println("Animal  Sound")
    println("-----      -----")

    animalNames.forEach { name ->
        val trimmed = name.trim().lowercase()

        val animal = try {
            AnimalFactory.createAnimal(trimmed)
        } catch (e: IllegalArgumentException) {
            System.err.println(e.message ?: "Unknown animal: '$name'")
            null
        }

        animal?.let {
            it.printYourName()
            print("    ")
            it.printYourSound()
            println()
        }
    }
}




fun main(arguments: Array<String>)
 {
    val (animalsFile, propertiesFile) = parseArguments(arguments)
    AnimalFactory.load(propertiesFile)
    val animalNames =parseFile(animalsFile)
    printAnimals(animalNames)
}