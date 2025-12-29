package app

import zoo.Animal

/**
 * Prints animals and their sounds in a formatted table-like output.
 * If an animal is null, an error message is printed instead.
 */
fun printAnimals(animals: Map<String, Animal?>) {
    println("Animal  Sound")
    println("-----   -----")

    animals.forEach { (key, animal) ->
        animal?.let {
            it.printYourName()
            print("    ")
            it.printYourSound()
            println()
        } ?: println("$key is not a valid animal")
    }
}