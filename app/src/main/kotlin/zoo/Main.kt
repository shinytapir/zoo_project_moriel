package zoo

import java.io.File
import java.io.IOException

fun readAnimalFile(fileName: String): List<String> {
    val fileContent = try {
        File(fileName).readText()
    } catch (e: IOException) {
        throw IOException("Could not read file '$fileName': ${e.message}")
    }

    val animalNames = fileContent
        .split("\\s+".toRegex())
        .filter { it.isNotBlank() }

    if (animalNames.isEmpty()) {
        throw IllegalArgumentException("No animal names found in file '$fileName'")
    }

    return animalNames
}

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        System.err.println("Please enter a file")
        return
    }

    val fileName = args[0]

    val animalNames = try {
        readAnimalFile(fileName)
    } catch (e: Exception) {
        System.err.println(e.message)
        return
    }

    println("Animal  Sound")
    println("-----      -----")

    for (animalName in animalNames) {
        try {
            val animal = AnimalFactory.createAnimal(animalName.trim().lowercase())
          println("%-10s %-10s".format(animal.printYournName(), animal.printYourSound()))
        } catch (e: IllegalArgumentException) {
            System.err.println(e.message)
        }
    }
}
