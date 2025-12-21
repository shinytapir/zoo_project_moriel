package zoo

import java.io.File
import java.io.IOException

fun readFromFile(fileName: String): List<String> {
    val fileContent = try {
        File(fileName).readText()
    } catch (e: IOException) {
        throw IOException("Could not read file '$fileName': ${e.message}")

    }
    val removeWhiteSpace="\\s+"
    val animalNames = fileContent
        .split(removeWhiteSpace.toRegex())
        .filter {it.isNotBlank() }

    if (animalNames.isEmpty()) {
        throw IllegalArgumentException("No animal names found in file '$fileName'")
    }

    return animalNames
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



fun main(args: Array<String>) {
    if (args.isEmpty()) {
        System.err.println("Please enter a file")
        return
    }
    val fileName = args[0]
    AnimalFactory.load(args[1])




    val animalNames = try {
        readFromFile(fileName)
    } catch (e: Exception) {
        System.err.println(e.message)
        return
    }
    printAnimals(animalNames)



}