

import java.io.File
import java.io.IOException
import kotlin.io.path.useLines
import kotlinx.cli.*
import com.jakewharton.picnic.table
import com.jakewharton.picnic.TextAlignment
import java.util.Scanner
import factories.PropertiesFactory
import zoo.Animal  
import config.AppConfig


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

//this function parse a file and returns a list of words 
private val noSpaces=Regex("\\s+")

fun parseFile(path: String): List<String> =
    File(path).useLines { lines ->
        lines.flatMap { line-> line.split(noSpaces) }
            .filter { word->word.isNotBlank() }
            .toList()
}



//this function creates a list of animals from a list of keys using a animals factory
fun createObjects(factory:PropertiesFactory<Animal>, keys: List<String>): List<Animal?> = 
   keys.map { key-> 
    runCatching{factory.create(key.lowercase()) }.getOrNull()
}




//this function prints a list of animals in a table format
fun printAnimals(animals: List<Animal?>) {
    println("Animal  Sound")
    println("-----      -----")

    animals.forEach { animal ->
        animal?.let {
            it.printYourName()
            print("    ")
            it.printYourSound()
            println()
        } ?: println("null")
    }
}





fun main(arguments: Array<String>)
 {
    val animalsFile = parseArguments(arguments)
    val animalNames= parseFile(animalsFile)
    val animalFactory = PropertiesFactory<Animal>(AppConfig.properties)
    val listAnimals = createObjects(animalFactory, animalNames)
    printAnimals(listAnimals)
     
}