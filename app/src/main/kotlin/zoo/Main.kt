package zoo

import java.io.File
import java.io.IOException

fun main(args: Array<String>) {

    if (args.isEmpty()) {
        System.err.println("Usage: <program> <filename>")
        return
    }

    val fileName = args[0]

    val fileContent = try {
        File(fileName).readText()
    } catch (e: IOException) {
        System.err.println("Could not find file '$fileName': ${e.message}")
        return
    }

    val animalNames = fileContent
        .split("\\s+".toRegex())
        .filter { it.isNotBlank() }

    if (animalNames.isEmpty()) {
        System.err.println("No animal names found in file")
        return
    }

    for (animalName in animalNames) {
        try {
            val animal = AnimalFactory.createAnimal(animalName)
            animal.print_your_name()
            animal.print_your_sound()
        } catch (e: IllegalArgumentException) {
            System.err.println(e.message)
        }
    }
}
