package zoo

import java.io.File
import java.io.IOException
import kotlinx.cli.*
import com.jakewharton.picnic.table
import com.jakewharton.picnic.TextAlignment
import java.util.Scanner


fun parseArguments(arguments: Array<String>):String {
    val parser = ArgParser("animals")

    val animalsFile by parser.option(
        ArgType.String,
        shortName = "a",
        description = "Animals file"
    ).required()

    parser.parse(arguments)
    return animalsFile
}



fun parseFile(fileName: String): List<String> {
    val words = mutableListOf<String>()
    Scanner(File(fileName)).use { scanner ->
        while (scanner.hasNext()) {
            words.add(scanner.next())
        }
    }
    return words
}




fun printAnimals(animalNames: List<String>) {

    println("Animal  Sound")
    println("-----      -----")

    animalNames.forEach { name ->
        
        val animal = try {
            val animalFactory = PropertiesFactory<Animal>(AppConfig.properties)
            animalFactory.create(name.lowercase())
        } catch (e: Exception) {
            System.err.println("$name is not a animal")
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
    val animalsFile = parseArguments(arguments)
    val animalNames= parseFile(animalsFile)
    printAnimals(animalNames)
}