import kotlinx.cli.*
import factories.PropertiesFactory
import zoo.Animal
import config.AppConfig
import app.*


/**
 * Parses command-line arguments and extracts the animals file path.
 */
fun parseArguments(arguments: Array<String>): String {
    val parser = ArgParser("animals")

    val animalsFile by parser.option(
        ArgType.String,
        shortName = "a",
        description = "Animals file"
    ).required()

    parser.parse(arguments)
    return animalsFile
}



fun main(arguments: Array<String>) {
    val animalsFile = parseArguments(arguments)
    val animalNames = parseFile(animalsFile)
    val animalFactory =PropertiesFactory<Animal>(AppConfig.properties, Animal::class)
    val listAnimals = createObjects(animalFactory, animalNames) 
    
    printAnimals(listAnimals)
}
