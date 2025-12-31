import kotlinx.cli.*
import factories.PropertiesFactory
import zoo.Animal
import config.AppConfig
import handlers.*


fun main(arguments: Array<String>) {
    val animalsFile = parseArguments(arguments)
    val animalNames = splitFile(animalsFile)
    val animalFactory = PropertiesFactory<Animal>(
        AppConfig.properties,
        Animal::class
    )

    val animalsList: List<Animal> 
    try {
        animalsList = animalFactory.create(animalNames)
    } catch (e: Exception) {
        println("Error creating animals: ${e.message}")
        return
    }

    printAnimals(animalsList)   
}



//input
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

//output
fun printAnimals(animals: List<Animal>) {
    println("Animal  Sound")
    println("-----   -----")

    animals.forEach { animal ->
        animal?.let {
            animal.printYourName()
            print("    ")
            animal.printYourSound()
            println()
        }
    }
}

