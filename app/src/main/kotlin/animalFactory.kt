package Zoo

object animalFactory : CarFactory {
    override fun createCar(model: String, year: Int): Car {
        println("Creating a family car!")
        return Car(model, year)
    }
}
