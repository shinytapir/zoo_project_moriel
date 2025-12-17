package zoo

object AnimalFactory {

    fun createAnimal(animalName: String): Animal {
        return when (animalName.trim().lowercase()) {
            "cat" -> Cat()
            "dog" -> Dog()
            "duck" -> Duck()
            else -> throw IllegalArgumentException(
                "Unknown animal: '$animalName'"
            )
        }
    }
}

