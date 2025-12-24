package zoo

import java.util.Properties
import java.io.FileInputStream

class AnimalPropertiesFactory(
    private val properties: Properties
) : PropertiesFactory<Animal>(
    Animal::class.java,
    properties
)



