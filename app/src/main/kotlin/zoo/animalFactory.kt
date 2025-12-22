package zoo

import java.io.FileInputStream
import java.util.Properties
/**
 * Factory object for creating instances of [Animal] subclasses.
 *
 * This object loads a properties file that maps animal type names
 * (like "dog" or "cat") to their fully-qualified class names. After loading,
 * it can create new instances of the corresponding [Animal] subclasses.
 *
 */
object AnimalFactory{
    
    /** Holds the mapping of animal type names to fully-qualified class names. */
    private val props = Properties()
    /** Loads a properties file containing mappings from animal type names to class names into props */
    fun load(propertiesPath:String){
        FileInputStream(propertiesPath).use{props.load(it)}
    }

    /**
     * Creates a new instance of an [Animal] subclass based on the given type name.
     *
     * The type must exist in the loaded properties file; otherwise, an
     * [IllegalArgumentException] is thrown.
     */
    fun createAnimal(type:String):Animal{
        val className = props.getProperty(type)
            ?: throw IllegalArgumentException("Unknown animal: '$type'")

        val animal= Class.forName(className)
        .getDeclaredConstructor()
        .newInstance() as Animal
        return animal 
    } 
}

