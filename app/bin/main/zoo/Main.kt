import kotlinx.cli.*
import factories.PropertiesFactory
import zoo.Animal
import config.AppConfig
import handlers.*

fun main(arguments: Array<String>) {
    val animalsFilePath = parseArguments(arguments)
    val animalNames = splitFile(animalsFilePath)

    val animalFactory = PropertiesFactory<Animal>(
        AppConfig.properties,
        Animal::class
    )

    
    try {
        val animals: List<Animal>  = animalNames.map { animalName -> animalFactory.create(animalName)}
        printAnimals(animals)
    } 
    catch (e: Exception) {
        println("Error creating animals: ${e.message}")
        return
    }

}

/**
 * Parses command-line arguments to get the path to the animals file.
 *
 * @param arguments The array of command-line arguments.
 * @return The path to the animals file as a [String].
 */
fun parseArguments(arguments: Array<String>): String {
    val parser = ArgParser("animals")

    val animalsFilePath by parser.option(
        ArgType.String,
        shortName = "a",
        description = "Path to the animals file"
    ).required()

    parser.parse(arguments)
    return animalsFilePath
}

/**
 * Prints a list of animals and their sounds in a formatted table.
 *
 * @param animals A list of [Animal] objects to print.
 */
fun printAnimals(animals: List<Animal>) {
    println("Animal  Sound")
    println("-----   -----")

    animals.forEach { animal ->
        animal.printYourName()
        print("    ")
        animal.printYourSound()
        println()
    }
}
