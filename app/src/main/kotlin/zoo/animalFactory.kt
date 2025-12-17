package zoo

object AnimalFactory {

    fun createAnimal(animalName: String): Animal {
        return when (animalName) {
            "cat" -> Cat()
            "dog" -> Dog()
            "duck" -> Duck()
            else -> throw IllegalArgumentException(
                "Unknown animal: '$animalName'"
            )
        }
    }
}

