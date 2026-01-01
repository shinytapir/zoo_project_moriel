import kotlinx.cli.*
import factories.PropertiesFactory
import zoo.Animal
import config.AppConfig
import handlers.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun main(arguments: Array<String>) {
    val logger: Logger = LoggerFactory.getLogger("main")
    logger.debug("started running")
    val animalsFilePath = parseArguments(arguments)
    logger.debug("working with animals file path: $animalsFilePath")
    val animalNames = splitFile(animalsFilePath)
    val animalFactory = PropertiesFactory<Animal>(
        AppConfig.properties,
        Animal::class
    )
    logger.info("Properties configuration loaded")
    
    try {
        val animals: List<Animal>  = animalNames.map { animalName -> animalFactory.create(animalName)}
        logger.info("Successfully created ${animals.size} animals")
        printAnimals(animals)
    } 
    catch (e: Exception) {
        println("Error creating animals: ${e.message}")
        logger.error("error creating animals: ${e}")
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
