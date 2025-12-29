package factories

import java.util.Properties
import kotlin.reflect.KClass

/**
 * A factory that creates objects based on class names defined in a [Properties] file.
 *
 * Each key in the properties file maps to a fully-qualified class name.
 * The factory uses reflection to create a new instance of that class and
 * verifies that it matches the expected type [T].
 *
 */
class PropertiesFactory<T : Any>(
    private val properties: Properties,
    private val expectedType: KClass<T>
) : Factory<T> {

    /**
     * Creates an instance associated with the given key.
     *
     * @throws IllegalArgumentException if the key does not exist
     *         or the created object is not of the expected type
     * @throws RuntimeException if the class cannot be instantiated
     */
    override fun create(key: String): T {
        val className = resolveClass(key)
        val result = createObject(className)

        return if (expectedType.isInstance(result)) {
             expectedType.cast(result)
            } else {
                throw IllegalArgumentException("$key is not of expected type.")
            }
    }

    /**
     * Resolves a key to a fully-qualified class name.
     *
     * @throws IllegalArgumentException if the key is not found
     */
    private fun resolveClass(key: String): String =
        properties.getProperty(key)
            ?: throw IllegalArgumentException("No class found for key: $key")

    /**
     * Creates an object instance from a class name using reflection.
     * The class must have a no-argument constructor.
     * @throws RuntimeException if instantiation fails
     */
    private fun createObject(className: String): Any = try {
        Class.forName(className)
            .getDeclaredConstructor()
            .newInstance()
    } catch (e: Exception) {
        throw RuntimeException("Failed to create instance of $className", e)
    }
}


