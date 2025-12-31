package factories

import java.util.Properties
import kotlin.reflect.KClass

/**
 * A factory that creates instances of classes based on keys stored in a [Properties] object.
 *
 * @param T The type of objects this factory creates. Must be non-nullable.
 * @property properties A [Properties] object that maps keys to fully qualified class names.
 * @param expectedType The [KClass] of the expected type to validate created objects.
 */
class PropertiesFactory<T : Any>(
    private val properties: Properties,
    expectedType: KClass<T>
) : Factory<T>(expectedType) {

    /**
     * Retrieves the fully qualified class name for a given key by reading properties.
     *
     * @param key The string key representing the object type.
     * @return The fully qualified class name corresponding to the key.
     * @throws IllegalArgumentException If no class is found for the given key.
     */
    override fun getClassNameForKey(key: String): String =
        properties.getProperty(key)
            ?: throw IllegalArgumentException("No class found for key: $key")
}

